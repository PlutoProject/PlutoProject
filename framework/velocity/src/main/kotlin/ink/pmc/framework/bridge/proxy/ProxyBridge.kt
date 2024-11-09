package ink.pmc.framework.bridge.proxy

import ink.pmc.framework.bridge.Bridge
import ink.pmc.framework.bridge.proxy.server.ProxyLocalServer
import ink.pmc.framework.bridge.server.BridgeServer
import ink.pmc.framework.bridge.world.BridgeWorld
import ink.pmc.framework.utils.data.mutableConcurrentListOf
import org.koin.java.KoinJavaComponent.getKoin

internal val proxyBridge: ProxyBridge
    get() = getKoin().get<Bridge>() as ProxyBridge

class ProxyBridge : Bridge {
    override val local: BridgeServer = ProxyLocalServer()
    override val master: BridgeServer = local
    override val servers: MutableList<BridgeServer> = mutableConcurrentListOf(master)
    override val worlds: Collection<BridgeWorld>
        get() = servers.filter { it != local }.flatMap { it.worlds }
}