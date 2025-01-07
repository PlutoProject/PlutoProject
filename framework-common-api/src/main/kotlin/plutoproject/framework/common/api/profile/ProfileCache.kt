package plutoproject.framework.common.api.profile

import plutoproject.framework.common.util.inject.Koin
import java.util.*

interface ProfileCache {
    companion object : ProfileCache by Koin.get()

    suspend fun getByName(name: String): CachedProfile?

    suspend fun getByUuid(uuid: UUID): CachedProfile?
}
