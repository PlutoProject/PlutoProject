package plutoproject.feature.paper.noCreeperBlockBreaks

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.bukkit.event.Listener
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "no_creeper_block_breaks",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class NoCreeperBlockBreaksFeature : PaperFeature(), Listener {
    override fun onEnable() {
        server.pluginManager.registerSuspendingEvents(ExplosionListener, plugin)
    }
}
