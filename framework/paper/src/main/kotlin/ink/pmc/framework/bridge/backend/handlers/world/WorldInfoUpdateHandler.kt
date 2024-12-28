package ink.pmc.framework.bridge.backend.handlers.world

import ink.pmc.framework.bridge.backend.handlers.NotificationHandler
import plutoproject.framework.common.bridge.debugInfo
import plutoproject.framework.common.bridge.internalBridge
import ink.pmc.framework.bridge.proto.BridgeRpcOuterClass.Notification

object WorldInfoUpdateHandler : NotificationHandler {
    override suspend fun handle(request: Notification) {
        if (request.worldInfoUpdate.server == internalBridge.local.id) return
        debugInfo("WorldInfoUpdateHandler: $request")
        internalBridge.updateRemoteWorldInfo(request.worldInfoUpdate)
    }
}