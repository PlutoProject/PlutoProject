package plutoproject.feature.paper.lecternProtection

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "lectern_protection",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class LecternProtectionFeature : PaperFeature() {
    override fun onEnable() {
        AnnotationParser.parse(LecternCommand)
        server.pluginManager.registerSuspendingEvents(LecternListener, plugin)
    }
}
