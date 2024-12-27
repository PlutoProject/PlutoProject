package plutoproject.framework.velocity.util.player

import com.velocitypowered.api.proxy.ConnectionRequestBuilder
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.future.await
import plutoproject.framework.velocity.util.server

suspend fun Player.switchServer(name: String): ConnectionRequestBuilder.Result {
    val registeredServer = server.getServer(name).get()
    val future = createConnectionRequest(registeredServer).connect()
    return future.await()
}
