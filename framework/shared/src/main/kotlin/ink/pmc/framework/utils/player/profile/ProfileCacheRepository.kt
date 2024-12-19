package ink.pmc.framework.utils.player.profile

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.kotlin.client.coroutine.MongoCollection
import ink.pmc.framework.player.profile.CachedProfile
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

class ProfileCacheRepository(private val collection: MongoCollection<CachedProfile>) {
    private val upsert = ReplaceOptions().upsert(true)

    suspend fun findByUuid(uuid: UUID): CachedProfile? {
        return collection.find(eq("uuid", uuid.toString())).firstOrNull()
    }

    suspend fun findByName(name: String): CachedProfile? {
        return collection.find(eq("name", name.lowercase())).firstOrNull()
    }

    suspend fun saveOrUpdate(profile: CachedProfile) {
        collection.replaceOne(eq("uuid", profile.uuid.toString()), profile, upsert)
    }
}