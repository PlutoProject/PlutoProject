package plutoproject.feature.velocity.playerConutLimiter.listeners

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PreLoginEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing.SamplePlayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.feature.velocity.playerConutLimiter.config.LimiterConfig
import plutoproject.feature.velocity.playerConutLimiter.serverIsFull
import plutoproject.framework.velocity.util.server

@Suppress("UNUSED")
object PingListener : KoinComponent {
    private val config by inject<LimiterConfig>()

    @Subscribe
    fun ProxyPingEvent.e() {
        ping = ping.asBuilder().apply {
            if (config.maxPlayerCount != -1) {
                maximumPlayers(config.maxPlayerCount)
            }
            if (config.forwardPlayerList) {
                val players = server.allPlayers
                    .map { SamplePlayer(it.username, it.uniqueId) }
                    .take(config.samplePlayersCount)
                samplePlayers(*players.toTypedArray())
            }
        }.build()
    }

    @Subscribe
    fun PreLoginEvent.e() {
        if (server.playerCount + 1 > config.maxPlayerCount && config.maxPlayerCount != -1) {
            result = PreLoginEvent.PreLoginComponentResult.denied(serverIsFull)
        }
    }
}
