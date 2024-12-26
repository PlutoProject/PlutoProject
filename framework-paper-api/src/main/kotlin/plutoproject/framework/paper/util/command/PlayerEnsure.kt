package plutoproject.framework.paper.util.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import plutoproject.framework.common.util.chat.NON_PLAYER

fun ensurePlayer(sender: CommandSender, action: Player.() -> Unit) {
    if (sender !is Player) {
        sender.sendMessage(NON_PLAYER)
        return
    }
    sender.action()
}

@JvmName("ensurePlayerReceiver")
fun CommandSender.ensurePlayer(action: Player.() -> Unit) {
    ensurePlayer(this, action)
}
