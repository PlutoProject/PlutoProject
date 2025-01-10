package plutoproject.feature.paper.serverSelector.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import plutoproject.feature.paper.serverSelector.startTimeSync
import plutoproject.feature.paper.serverSelector.stopTimeSync

@Suppress("UNUSED")
object TimeSyncListener : Listener {
    @EventHandler
    fun PlayerJoinEvent.e() {
        player.startTimeSync()
    }

    @EventHandler
    fun PlayerQuitEvent.e() {
        player.stopTimeSync()
    }
}
