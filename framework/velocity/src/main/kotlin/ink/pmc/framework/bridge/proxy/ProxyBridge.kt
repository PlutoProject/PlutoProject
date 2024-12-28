package ink.pmc.framework.bridge.proxy

import plutoproject.framework.common.bridge.InternalBridge
import plutoproject.framework.common.bridge.internalBridge
import plutoproject.framework.common.bridge.player.InternalPlayer
import ink.pmc.framework.bridge.proto.BridgeRpcOuterClass.PlayerInfo
import ink.pmc.framework.bridge.proxy.player.ProxyRemoteBackendPlayer
import ink.pmc.framework.bridge.proxy.server.ProxyLocalServer
import ink.pmc.framework.bridge.server.BridgeServer
import plutoproject.framework.common.bridge.server.InternalServer
import plutoproject.framework.common.bridge.throwRemoteServerNotFound
import plutoproject.framework.common.bridge.warn
import ink.pmc.framework.platform.proxy
import ink.pmc.framework.player.uuid

class ProxyBridge : InternalBridge() {
    override val local: BridgeServer = ProxyLocalServer()

    init {
        servers.add(local)
    }

    override fun createRemotePlayer(info: PlayerInfo, server: InternalServer?): InternalPlayer? {
        val actualServer = server ?: getInternalRemoteServer(info.server)
        if (actualServer == null) {
            warn { throwRemoteServerNotFound(info.server) }
            return null
        }
        val remoteWorld = internalBridge.getInternalRemoteWorld(actualServer, info.world.name)
        return ProxyRemoteBackendPlayer(proxy.getPlayer(info.uniqueId.uuid).get(), actualServer, remoteWorld)
    }
}