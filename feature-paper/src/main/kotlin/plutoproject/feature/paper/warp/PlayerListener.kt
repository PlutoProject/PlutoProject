package plutoproject.feature.paper.warp

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.koin.core.component.KoinComponent
import plutoproject.feature.paper.api.warp.WarpManager

@Suppress("UNUSED")
object PlayerListener : Listener, KoinComponent {
    @EventHandler
    suspend fun PlayerJoinEvent.e() {
        if (WarpManager.getPreferredSpawn(player) != null) return
        val default = WarpManager.getDefaultSpawn() ?: return
        WarpManager.setPreferredSpawn(player, default)
    }
}
