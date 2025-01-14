package plutoproject.feature.paper.noJoinQuitMessage

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "no_join_quit_message",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class NoJoinQuitMessageFeature : PaperFeature() {
    override fun onEnable() {
        server.pluginManager.registerSuspendingEvents(NoJoinQuitMessageListener, plugin)
    }
}
