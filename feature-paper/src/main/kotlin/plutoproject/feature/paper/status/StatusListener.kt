package plutoproject.feature.paper.status

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("UNUSED")
object StatusListener : Listener, KoinComponent {
    private val config by inject<StatusConfig>()

    @EventHandler(ignoreCancelled = true)
    fun PlayerCommandPreprocessEvent.e() {
        if (message == "/tps" && config.overrideTpsCommand) {
            player.performCommand("plutoproject_hypervisor:tps")
            isCancelled = true
            return
        }
        if (message == "/mspt" && config.overrideMsptCommand) {
            player.performCommand("plutoproject_hypervisor:mspt")
            isCancelled = true
            return
        }
    }
}
