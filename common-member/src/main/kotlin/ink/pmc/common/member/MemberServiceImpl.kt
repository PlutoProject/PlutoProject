package ink.pmc.common.member

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.mongodb.client.model.Filters.*
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import ink.pmc.common.member.api.AuthType
import ink.pmc.common.member.api.Member
import ink.pmc.common.member.api.WhitelistStatus
import ink.pmc.common.member.api.data.DataContainer
import ink.pmc.common.member.api.data.MemberModifier
import ink.pmc.common.member.comment.AbstractComment
import ink.pmc.common.member.comment.AbstractCommentRepository
import ink.pmc.common.member.data.AbstractBedrockAccount
import ink.pmc.common.member.data.AbstractDataContainer
import ink.pmc.common.member.data.BedrockAccountImpl
import ink.pmc.common.member.data.DataContainerImpl
import ink.pmc.common.member.punishment.AbstractPunishment
import ink.pmc.common.member.storage.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicReference

@Suppress("UNUSED")
const val UID_START = 10000L

@Suppress("UNUSED")
class MemberServiceImpl(
    database: MongoDatabase
) : AbstractMemberService() {

    override val status: MongoCollection<StatusStorage> = database.getCollection("member_status")
    override val members: MongoCollection<MemberStorage> = database.getCollection("member_members")
    override val punishments: MongoCollection<PunishmentStorage> = database.getCollection("member_punishments")
    override val comments: MongoCollection<CommentStorage> = database.getCollection("member_comments")
    override val dataContainers: MongoCollection<DataContainerStorage> =
        database.getCollection("member_data_containers")
    override val bedrockAccounts: MongoCollection<BedrockAccountStorage> =
        database.getCollection("member_bedrock_accounts")
    override val loadedMembers: LoadingCache<Long, Member> = Caffeine.newBuilder()
        .refreshAfterWrite(Duration.ofMinutes(10))
        .build { runBlocking { loadMember(it) } }
    override val currentStatus: AtomicReference<StatusStorage> = AtomicReference()
    override suspend fun lookupMemberStorage(uid: Long): MemberStorage? {
        return members.find(eq("uid", uid)).firstOrNull()
    }

    override suspend fun lookupPunishmentStorage(id: Long): PunishmentStorage? {
        return punishments.find(eq("id", id)).firstOrNull()
    }

    override suspend fun lookupCommentStorage(id: Long): CommentStorage? {
        return comments.find(eq("id", id)).firstOrNull()
    }

    override suspend fun lookupDataContainerStorage(id: Long): DataContainerStorage? {
        return dataContainers.find(eq("id", id)).firstOrNull()
    }

    override suspend fun lookupBedrockAccount(id: Long): BedrockAccountStorage? {
        return bedrockAccounts.find(eq("id", id)).firstOrNull()
    }

    private val service = this

    private suspend fun loadMember(uid: Long): Member {
        val memberStorage = members.find(eq("uid", uid)).firstOrNull()!!
        return createMemberInstance(memberStorage)
    }

    private suspend fun createDataContainerInstance(storage: MemberStorage): DataContainer {
        return DataContainerImpl(this, lookupDataContainerStorage(storage.dataContainer)!!)
    }

    private suspend fun createMemberInstance(storage: MemberStorage): Member {
        val dataContainer = createDataContainerInstance(storage)
        if (storage.bedrockAccount != null) {
            val bedrockAccount = BedrockAccountImpl(this, lookupBedrockAccount(storage.bedrockAccount!!)!!)
            return MemberImpl(service, storage, dataContainer, bedrockAccount)
        }

        return MemberImpl(service, storage, dataContainer, null)
    }

    override suspend fun lastUid(): Long {
        return currentStatus.get().lastMember
    }

    override suspend fun lastMember(): Member? {
        return lookup(currentStatus.get().lastMember)
    }

    override suspend fun lastMemberCreatedAt(): Instant? {
        return lastMember()?.createdAt
    }

    init {
        runBlocking {
            var lookupStatus = status.find(exists("lastMember")).firstOrNull()

            if (lookupStatus == null) {
                lookupStatus = StatusStorage(ObjectId(), -1, -1, -1, -1, -1)
                status.insertOne(lookupStatus)
            }

            currentStatus.set(lookupStatus)
        }
    }

    override suspend fun create(name: String, authType: AuthType): Member? {
        var memberStorage = members.find(
            and(
                eq("name", name),
                eq("authType", authType.toString())
            )
        ).firstOrNull()

        if (memberStorage != null) {
            return loadedMembers.get(memberStorage.uid)
        }

        val id = authType.fetcher.fetch(name) ?: return null
        val nextMember = currentStatus.get().nextMember()
        val nextDataContainer = currentStatus.get().nextDataContainer()

        memberStorage = MemberStorage(
            ObjectId(),
            nextMember,
            id.toString(),
            name,
            WhitelistStatus.WHITELISTED.toString(),
            authType.toString(),
            System.currentTimeMillis(),
            null,
            null,
            nextDataContainer,
            null,
            null,
            mutableListOf(),
            mutableListOf()
        )
        val dataContainerStorage = DataContainerStorage(
            ObjectId(),
            nextDataContainer,
            nextMember,
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            mutableMapOf()
        )
        val dataContainer = DataContainerImpl(service, dataContainerStorage)


        currentStatus.get().increaseDataContainer()
        currentStatus.get().increaseMember()

        val member = MemberImpl(this, memberStorage, dataContainer, null)
        loadedMembers.put(nextMember, member)
        update(member)

        return member
    }

    override suspend fun lookup(uid: Long): Member? = withContext(Dispatchers.IO) {
        if (!exist(uid)) {
            return@withContext null
        }

        loadedMembers.get(uid)
    }

    override suspend fun lookup(uuid: UUID): Member? {
        if (!exist(uuid)) {
            return null
        }

        val storage = members.find(eq("id", uuid.toString())).firstOrNull() ?: return null
        return createMemberInstance(storage)
    }

    override fun get(uid: Long): Member? = runBlocking {
        lookup(uid)
    }

    override suspend fun exist(uid: Long): Boolean {
        val memberStorage = members.find(eq("uid", uid)).firstOrNull()
        return memberStorage != null
    }

    override suspend fun exist(uuid: UUID): Boolean {
        return members.find(eq("id", uuid.toString())).firstOrNull() != null
    }

    override suspend fun existPunishment(id: Long): Boolean {
        val punishmentStorage = punishments.find(eq("id", id)).firstOrNull()
        return punishmentStorage != null
    }

    override suspend fun existComment(id: Long): Boolean {
        val commentStorage = comments.find(eq("id", id)).firstOrNull()
        return commentStorage != null
    }

    override suspend fun existDataContainer(id: Long): Boolean {
        val dataContainerStorage = dataContainers.find(eq("id", id)).firstOrNull()
        return dataContainerStorage != null
    }

    override suspend fun existBedrockAccount(id: Long): Boolean {
        val bedrockAccountStorage = bedrockAccounts.find(eq("id", id)).firstOrNull()
        return bedrockAccountStorage != null
    }

    override suspend fun isWhitelisted(uid: Long): Boolean {
        return exist(uid) && lookup(uid)!!.isWhitelisted
    }

    override suspend fun isWhitelisted(uuid: UUID): Boolean {
        return exist(uuid) && lookup(uuid)!!.isWhitelisted
    }

    override suspend fun modifier(uid: Long, refresh: Boolean): MemberModifier? {
        if (!exist(uid)) {
            return null
        }

        val member = lookup(uid)

        if (refresh) {
            member!!.refresh()
        }

        return member!!.modifier
    }

    override suspend fun modifier(uuid: UUID, refresh: Boolean): MemberModifier? {
        if (!exist(uuid)) {
            return null
        }

        val member = lookup(uuid)

        if (refresh) {
            member!!.refresh()
        }

        return member!!.modifier
    }

    private suspend fun updateMember(member: MemberStorage) {
        members.deleteOne(eq("uid", member.uid))
        members.insertOne(member)
    }

    private suspend fun updatePunishment(punishment: PunishmentStorage) {
        punishments.deleteOne(eq("id", punishment.id))
        punishments.insertOne(punishment)
    }

    private suspend fun updateComment(comment: CommentStorage, removal: Boolean = false) {
        comments.deleteOne(eq("id", comment.id))

        if (!removal) {
            comments.insertOne(comment)
        }
    }

    private suspend fun updateDataContainer(dataContainer: DataContainerStorage) {
        dataContainers.deleteOne(eq("id", dataContainer.id))
        dataContainers.insertOne(dataContainer)
    }

    private suspend fun updateBedrockAccount(bedrockAccountStorage: BedrockAccountStorage, removal: Boolean = false) {
        bedrockAccounts.deleteOne(eq("id", bedrockAccountStorage.id))

        if (!removal) {
            bedrockAccounts.insertOne(bedrockAccountStorage)
        }
    }

    private suspend fun updateStatus(statusStorage: StatusStorage) {
        status.deleteOne(exists("lastMember"))
        status.insertOne(statusStorage)
    }

    override suspend fun update(member: Member) {
        withContext(Dispatchers.IO) {
            if (loadedMembers.getIfPresent(member.uid) == null) {
                return@withContext
            }

            // 使用 run 包裹，来在不同的部分使用一样的临时变量名
            run {
                val storage = (member as AbstractMember).storage
                val punishments = member.punishmentLogger.historyPunishments.map { it.id }.toMutableList()
                val comments = member.commentRepository.comments.map { it.id }.toMutableList()
                val newStorage = MemberStorage(
                    storage.objectId,
                    member.uid,
                    member.id.toString(),
                    member.name,
                    member.whitelistStatus.toString(),
                    member.authType.toString(),
                    member.createdAt.toEpochMilli(),
                    member.lastJoinedAt?.toEpochMilli(),
                    member.lastQuitedAt?.toEpochMilli(),
                    member.dataContainer.id,
                    member.bedrockAccount?.id,
                    member.bio,
                    punishments,
                    comments
                )
                updateMember(newStorage)
            }

            run {
                member.punishmentLogger.historyPunishments.forEach {
                    val objectId = (it as AbstractPunishment).storage.objectId
                    val storage = PunishmentStorage(
                        objectId,
                        it.id,
                        it.type.toString(),
                        it.time.toEpochMilli(),
                        it.belongs.uid,
                        it.isRevoked,
                        it.executor.uid
                    )

                    updatePunishment(storage)
                }
            }

            run {
                val repo = member.commentRepository as AbstractCommentRepository
                repo.comments.forEach {
                    val abs = (it as AbstractComment)
                    val obj = abs.storage.objectId
                    val storage = CommentStorage(
                        obj,
                        it.id,
                        it.createdAt.toEpochMilli(),
                        it.creator.uid,
                        it.content,
                        it.isModified
                    )

                    updateComment(storage)
                    repo.dirtyComments.forEach { dirty -> updateComment(dirty, true) }
                    repo.dirtyComments.clear()
                }
            }

            run {
                val container = member.dataContainer as AbstractDataContainer
                val obj = container.storage.objectId
                val storage = DataContainerStorage(
                    obj,
                    container.id,
                    container.owner.uid,
                    container.createdAt.toEpochMilli(),
                    container.lastModifiedAt.toEpochMilli(),
                    container.contents.toMutableMap()
                )
                updateDataContainer(storage)
            }

            if (member.bedrockAccount == null) {
                return@withContext
            }

            run {
                val abs = member as AbstractMember
                val account = member.bedrockAccount as AbstractBedrockAccount
                val obj = account.storage.objectId
                val storage = BedrockAccountStorage(
                    obj,
                    account.id,
                    account.linkedWith.uid,
                    account.xuid,
                    account.gamertag
                )
                updateBedrockAccount(storage)
                abs.dirtyBedrockAccounts.forEach { dirty -> updateBedrockAccount(dirty, true) }
                abs.dirtyBedrockAccounts.clear()
            }

            updateStatus(currentStatus.get())
        }
    }

    override suspend fun update(uid: Long) {
        val member = loadedMembers.asMap().values.firstOrNull { it.uid == uid }
        if (member == null) {
            return
        }

        update(loadedMembers.get(uid))
    }

    override suspend fun update(uuid: UUID) {
        val member = loadedMembers.asMap().values.firstOrNull { it.id == uuid }
        if (member == null) {
            return
        }

        update(member)
    }

    override suspend fun refresh(member: Member): Member? {
        if (!exist(member.uid)) {
            return null
        }

        currentStatus.set(status.find(exists("lastMember")).firstOrNull())
        loadedMembers.invalidate(member.uid)
        return lookup(member.uid)
    }

    override suspend fun refresh(uid: Long): Member? {
        if (!exist(uid)) {
            return null
        }

        currentStatus.set(status.find(exists("lastMember")).firstOrNull())
        loadedMembers.invalidate(uid)
        return lookup(uid)
    }

    override suspend fun refresh(uuid: UUID): Member? {
        if (!exist(uuid)) {
            return null
        }

        val member = lookup(uuid)
        currentStatus.set(status.find(exists("lastMember")).firstOrNull())
        loadedMembers.invalidate(member!!.uid)
        return lookup(uuid)
    }

}