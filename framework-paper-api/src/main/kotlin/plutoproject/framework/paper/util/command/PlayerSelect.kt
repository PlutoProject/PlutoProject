package plutoproject.framework.paper.util.command

import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

inline fun <reified T : OfflinePlayer> selectPlayer(self: CommandSender, other: T?): T? {
    return other ?: self as? T
}
