package plutoproject.framework.velocity.util.command

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import plutoproject.framework.common.util.chat.NON_PLAYER

fun ensurePlayer(sender: CommandSource, action: Player.() -> Unit) {
    if (sender !is Player) {
        sender.sendMessage(NON_PLAYER)
        return
    }
    sender.action()
}

fun CommandSource.ensurePlayer(action: Player.() -> Unit) {
    ensurePlayer(this, action)
}
