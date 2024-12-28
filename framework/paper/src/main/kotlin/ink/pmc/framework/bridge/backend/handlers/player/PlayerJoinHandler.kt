package ink.pmc.framework.bridge.backend.handlers.player

import ink.pmc.framework.bridge.backend.handlers.NotificationHandler
import plutoproject.framework.common.bridge.debugInfo
import plutoproject.framework.common.bridge.internalBridge
import ink.pmc.framework.bridge.proto.BridgeRpcOuterClass.Notification

object PlayerJoinHandler : NotificationHandler {
    override suspend fun handle(request: Notification) {
        if (request.playerJoin.server == internalBridge.local.id) return
        debugInfo("PlayerJoinHandler: $request")
        internalBridge.addRemotePlayer(request.playerJoin)
    }
}