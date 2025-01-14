package plutoproject.feature.paper.afk

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent
import plutoproject.feature.paper.api.afk.AfkManager

@Suppress("UNUSED")
object PlayerListener : Listener {
    private fun Player.unAfk() {
        if (AfkManager.isAfk(this)) {
            AfkManager.toggle(this)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun PlayerMoveEvent.e() {
        if (!hasExplicitlyChangedBlock()) return
        player.unAfk()
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun AsyncChatEvent.e() {
        if (isCancelled) return
        player.unAfk()
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun PlayerTeleportEvent.e() {
        if (isCancelled) return
        player.unAfk()
    }
}
