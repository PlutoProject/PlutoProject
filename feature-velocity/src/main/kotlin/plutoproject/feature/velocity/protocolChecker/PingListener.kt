package plutoproject.feature.velocity.protocolChecker

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PreLoginEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("UNUSED")
object PingListener : KoinComponent {
    private val config by inject<ProtocolCheckerConfig>()

    @Subscribe(order = PostOrder.FIRST)
    fun ProxyPingEvent.e() {
        val clientVersion = connection.protocolVersion
        val reportVersion = if (clientVersion.protocol in config.intProtocolRange) {
            clientVersion.protocol
        } else {
            config.intProtocolRange.first
        }
        val version = ServerPing.Version(
            reportVersion,
            if (config.serverBrand != null) "${config.serverBrand} $versionRange" else versionRange
        )
        ping = ping.asBuilder().version(version).build()
    }

    @Subscribe(order = PostOrder.FIRST)
    fun PreLoginEvent.e() {
        val protocol = connection.protocolVersion.protocol
        if (protocol !in config.intProtocolRange) {
            result = PreLoginEvent.PreLoginComponentResult.denied(versionNotSupported)
        }
    }
}
