package plutoproject.feature.paper.back

import org.bukkit.Location
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.feature.paper.api.back.BackManager
import plutoproject.feature.paper.api.back.BackTeleportEvent
import plutoproject.feature.paper.api.teleport.TeleportManager
import plutoproject.framework.common.util.coroutine.runAsync
import plutoproject.framework.common.util.coroutine.withDefault

class BackManagerImpl : BackManager, KoinComponent {
    private val repo by inject<BackRepository>()

    override suspend fun has(player: Player): Boolean {
        return repo.has(player)
    }

    override suspend fun get(player: Player): Location? {
        return repo.find(player)
    }

    override fun back(player: Player) {
        runAsync {
            backSuspend(player)
        }
    }

    override suspend fun backSuspend(player: Player) {
        withDefault {
            val loc = requireNotNull(get(player)) { "Player ${player.name} doesn't have a back location" }
            // 必须异步触发
            val event = BackTeleportEvent(player, player.location, loc).apply { callEvent() }
            if (event.isCancelled) return@withDefault
            set(player, player.location)
            val opt = TeleportManager.getWorldTeleportOptions(loc.world).copy(disableSafeCheck = true)
            TeleportManager.teleportSuspend(player, loc, opt)
        }
    }

    override suspend fun set(player: Player, location: Location) {
        val loc = if (TeleportManager.isSafe(location)) {
            location
        } else {
            TeleportManager.searchSafeLocationSuspend(location) ?: return
        }
        repo.save(player, loc)
    }

    override suspend fun remove(player: Player) {
        repo.delete(player)
    }
}
