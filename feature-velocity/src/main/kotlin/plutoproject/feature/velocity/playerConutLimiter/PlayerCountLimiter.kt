package plutoproject.feature.velocity.playerConutLimiter

import com.github.shynixn.mccoroutine.velocity.registerSuspend
import org.koin.dsl.module
import plutoproject.feature.velocity.playerConutLimiter.config.LimiterConfig
import plutoproject.feature.velocity.protocolChecker.listeners.PingListener
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.common.util.config.loadConfig
import plutoproject.framework.common.util.inject.configureKoin
import plutoproject.framework.velocity.api.feature.VelocityFeature
import plutoproject.framework.velocity.util.plugin
import plutoproject.framework.velocity.util.server

@Feature(
    id = "player_count_limiter",
    platform = Platform.VELOCITY,
)
@Suppress("UNUSED")
class PlayerCountLimiter : VelocityFeature() {
    private val featureModule = module {
        single<LimiterConfig> { loadConfig(saveConfig()) }
    }

    override fun onEnable() {
        configureKoin {
            modules(featureModule)
        }
        server.eventManager.registerSuspend(plugin, PingListener)
    }
}
