package plutoproject.feature.paper.sit

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.koin.dsl.module
import plutoproject.feature.paper.api.sit.SitManager
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.common.util.inject.configureKoin
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

var disabled = true

@Feature(
    id = "sit",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class SitFeature : PaperFeature() {
    private val featureModule = module {
        single<SitManager> { SitManagerImpl() }
    }

    override fun onEnable() {
        configureKoin {
            modules(featureModule)
        }
        AnnotationParser.parse(SitCommand)
        server.pluginManager.registerSuspendingEvents(SitListener, plugin)
        runSitCheckTask()
        runActionBarOverrideTask()
        disabled = false
    }

    override fun onDisable() {
        disabled = true
    }
}
