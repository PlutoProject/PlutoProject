package plutoproject.feature.paper.dynamicScheduling

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import plutoproject.feature.paper.api.dynamicScheduling.DynamicScheduling
import plutoproject.feature.paper.api.menu.MenuManager
import plutoproject.feature.paper.api.menu.isMenuAvailable
import plutoproject.feature.paper.dynamicScheduling.buttons.ViewBoost
import plutoproject.feature.paper.dynamicScheduling.buttons.ViewBoostButtonDescriptor
import plutoproject.feature.paper.dynamicScheduling.commands.DynamicSchedulingCommand
import plutoproject.feature.paper.dynamicScheduling.config.DynamicSchedulingConfig
import plutoproject.feature.paper.dynamicScheduling.listeners.DynamicViewDistanceListener
import plutoproject.framework.common.api.feature.Load
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Dependency
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.common.api.options.OptionsManager
import plutoproject.framework.common.util.config.loadConfig
import plutoproject.framework.common.util.inject.configureKoin
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.api.statistic.StatisticProvider
import plutoproject.framework.paper.util.command.AnnotationParser
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server
import java.util.logging.Logger

var disabled = true
internal lateinit var featureLogger: Logger

@Feature(
    id = "dynamic_scheduling",
    platform = Platform.PAPER,
    dependencies = [
        Dependency(id = "menu", load = Load.AFTER, required = false),
    ],
)
@Suppress("UNUSED")
class DynamicScheduling : PaperFeature(), KoinComponent {
    private val config by inject<DynamicSchedulingConfig>()
    private val featureModule = module {
        single<DynamicSchedulingConfig> { loadConfig(saveConfig()) }
        single<DynamicScheduling> { DynamicSchedulingImpl() }
    }

    override fun onEnable() {
        featureLogger = logger
        configureKoin {
            modules(featureModule)
        }
        logger.info("Using statistic provider: ${StatisticProvider.type}")
        AnnotationParser.parse(DynamicSchedulingCommand)
        if (isMenuAvailable) {
            MenuManager.registerButton(ViewBoostButtonDescriptor) { ViewBoost() }
        }
        OptionsManager.registerOptionDescriptor(DynamicViewDistanceOptionDescriptor)
        server.pluginManager.registerSuspendingEvents(DynamicViewDistanceListener, plugin)
        DynamicScheduling.start()
    }

    override fun onDisable() {
        DynamicScheduling.stop()
        disabled = true
    }
}
