package plutoproject.feature.paper.randomTeleport

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.koin.core.component.KoinComponent
import plutoproject.feature.paper.api.randomTeleport.RandomTeleportManager

@Suppress("UNUSED", "UnusedReceiverParameter")
object PlayerListener : Listener, KoinComponent {
    @EventHandler
    suspend fun ServerTickEndEvent.e() {
        RandomTeleportManager.tick()
    }
}
