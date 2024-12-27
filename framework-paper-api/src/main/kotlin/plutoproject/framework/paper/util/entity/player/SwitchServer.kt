package plutoproject.framework.paper.util.entity.player

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import plutoproject.framework.common.util.coroutine.runAsyncIO
import plutoproject.framework.common.util.coroutine.withIO
import plutoproject.framework.paper.util.plugin

suspend fun Player.switchServer(name: String) {
    withIO {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("Connect")
        out.writeUTF(name)
        player?.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
    }
}

fun Player.switchServerAsync(name: String) = runAsyncIO {
    switchServer(name)
}
