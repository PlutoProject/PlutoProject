package plutoproject.feature.paper.home

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.koin.core.component.KoinComponent
import plutoproject.feature.paper.api.home.HomeManager

@Suppress("UNUSED", "UnusedReceiverParameter")
object PlayerListener : Listener, KoinComponent {
    @EventHandler
    suspend fun PlayerJoinEvent.e() {
        // 加载所有家
        HomeManager.list(player)
    }

    @EventHandler
    fun PlayerQuitEvent.e() {
        HomeManager.unloadAll(player)
    }
}
