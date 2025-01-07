package plutoproject.feature.paper.farmProtect

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "farm_protection",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class FarmProtect : PaperFeature() {
    override fun onEnable() {
        server.pluginManager.registerSuspendingEvents(InteractionListener, plugin)
    }
}
