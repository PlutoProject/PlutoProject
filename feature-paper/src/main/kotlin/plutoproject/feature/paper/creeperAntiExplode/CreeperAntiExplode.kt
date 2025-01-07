package plutoproject.feature.paper.creeperAntiExplode

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.bukkit.event.Listener
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "creeper_anti_explode",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class CreeperAntiExplode : PaperFeature(), Listener {
    override fun onEnable() {
        server.pluginManager.registerSuspendingEvents(ExplodeListener, plugin)
    }
}
