package ink.pmc.framework.bridge.backend.handlers.world

import ink.pmc.framework.bridge.backend.handlers.NotificationHandler
import plutoproject.framework.common.bridge.debugInfo
import plutoproject.framework.common.bridge.internalBridge
import ink.pmc.framework.bridge.proto.BridgeRpcOuterClass.Notification

object WorldLoadHandler : NotificationHandler {
    override suspend fun handle(request: Notification) {
        if (request.worldLoad.server == internalBridge.local.id) return
        debugInfo("WorldLoadHandler: $request")
        internalBridge.addRemoteWorld(request.worldLoad)
    }
}