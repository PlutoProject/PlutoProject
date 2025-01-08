package plutoproject.feature.paper.farmProtection

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import plutoproject.feature.paper.farmProtection.listeners.InteractionListener
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
class FarmProtection : PaperFeature() {
    override fun onEnable() {
        server.pluginManager.registerSuspendingEvents(InteractionListener, plugin)
    }
}
