package plutoproject.feature.paper.back

import ink.pmc.essentials.api.home.HomeTeleportEvent
import ink.pmc.essentials.api.warp.WarpTeleportEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause
import org.koin.core.component.KoinComponent
import plutoproject.feature.paper.api.back.BackManager
import plutoproject.feature.paper.api.randomTeleport.events.RandomTeleportEvent
import plutoproject.feature.paper.api.teleport.RequestState
import plutoproject.feature.paper.api.teleport.events.RequestStateChangeEvent

@Suppress("UNUSED", "UnusedReceiverParameter")
object PlayerListener : Listener, KoinComponent {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    suspend fun HomeTeleportEvent.e() {
        manager.set(player, from)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    suspend fun WarpTeleportEvent.e() {
        manager.set(player, from)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    suspend fun RandomTeleportEvent.e() {
        BackManager.set(player, from)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    suspend fun PlayerDeathEvent.e() {
        BackManager.set(player, player.location)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    suspend fun RequestStateChangeEvent.e() {
        if (after != RequestState.ACCEPTED) return
        val player = request.source
        BackManager.set(player, player.location)
    }

    private val validTeleportCauses = arrayOf(
        TeleportCause.COMMAND,
        TeleportCause.END_GATEWAY,
        TeleportCause.END_PORTAL,
        TeleportCause.NETHER_PORTAL,
        TeleportCause.SPECTATE,
        TeleportCause.UNKNOWN,
    )

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    suspend fun PlayerTeleportEvent.e() {
        if (!validTeleportCauses.contains(cause)) return
        BackManager.set(player, player.location)
    }
}
