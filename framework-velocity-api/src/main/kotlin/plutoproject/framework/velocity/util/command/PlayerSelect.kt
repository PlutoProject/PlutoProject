package plutoproject.framework.velocity.util.command

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player

inline fun <reified T : Player> selectPlayer(self: CommandSource, other: T?): T? {
    return other ?: self as? T
}
