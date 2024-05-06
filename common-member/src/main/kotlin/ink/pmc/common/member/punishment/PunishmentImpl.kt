package ink.pmc.common.member.punishment

import ink.pmc.common.member.AbstractMemberService
import ink.pmc.common.member.api.Member
import ink.pmc.common.member.api.punishment.PunishmentType
import ink.pmc.common.member.storage.PunishmentStorage
import java.time.Instant

class PunishmentImpl(
    override val belongs: Member,
    private val service: AbstractMemberService,
    override val storage: PunishmentStorage
) :
    AbstractPunishment() {

    override val id: Long = storage.id
    override val type: PunishmentType = PunishmentType.valueOf(storage.type)
    override val time: Instant = Instant.ofEpochMilli(storage.time)
    override var isRevoked: Boolean = storage.isRevoked

    override suspend fun executor(): Member {
        return service.lookup(storage.executor)!!
    }

}