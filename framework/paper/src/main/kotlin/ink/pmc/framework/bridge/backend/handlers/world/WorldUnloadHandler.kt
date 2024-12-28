package ink.pmc.framework.bridge.backend.handlers.world

import ink.pmc.framework.bridge.backend.handlers.NotificationHandler
import plutoproject.framework.common.bridge.debugInfo
import plutoproject.framework.common.bridge.internalBridge
import ink.pmc.framework.bridge.proto.BridgeRpcOuterClass

object WorldUnloadHandler : NotificationHandler {
    override suspend fun handle(request: BridgeRpcOuterClass.Notification) {
        if (request.worldUnload.server == internalBridge.local.id) return
        debugInfo("WorldUnloadHandler: $request")
        internalBridge.removeRemoteWorld(request.worldUnload)
    }
}