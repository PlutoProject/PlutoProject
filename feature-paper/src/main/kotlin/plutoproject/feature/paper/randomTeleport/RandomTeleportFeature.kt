package plutoproject.feature.paper.randomTeleport

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import org.incendo.cloud.bukkit.parser.WorldParser
import org.koin.dsl.module
import plutoproject.feature.paper.api.menu.MenuManager
import plutoproject.feature.paper.api.menu.isMenuAvailable
import plutoproject.feature.paper.api.randomTeleport.RandomTeleportManager
import plutoproject.framework.common.api.feature.Load
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Dependency
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.common.util.config.loadConfig
import plutoproject.framework.common.util.inject.configureKoin
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser
import plutoproject.framework.paper.util.command.CommandManager
import plutoproject.framework.paper.util.command.PaperPrivilegedSuggestion
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "random_teleport",
    platform = Platform.PAPER,
    dependencies = [
        Dependency(id = "teleport", load = Load.BEFORE, required = true),
        Dependency(id = "menu", load = Load.BEFORE, required = false),
    ],
)
@Suppress("UNUSED")
class RandomTeleportFeature : PaperFeature() {
    private val featureModule = module {
        single<RandomTeleportConfig> { loadConfig(saveConfig()) }
        single<RandomTeleportManager> { RandomTeleportManagerImpl() }
    }

    override fun onEnable() {
        configureKoin {
            modules(featureModule)
        }
        CommandManager.parserRegistry().apply {
            registerSuggestionProvider(
                "rtp-world",
                PaperPrivilegedSuggestion.of(WorldParser(), RANDOM_TELEPORT_SPECIFIC_PERMISSION)
            )
        }
        AnnotationParser.parse(RtpCommand)
        server.pluginManager.registerSuspendingEvents(PlayerListener, plugin)
        if (isMenuAvailable) {
            MenuManager.registerButton(RandomTeleportButtonDescriptor) { RandomTeleport() }
        }
    }
}
