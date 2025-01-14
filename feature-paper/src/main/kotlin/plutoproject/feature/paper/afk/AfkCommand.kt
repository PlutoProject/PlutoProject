package plutoproject.feature.paper.afk

import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Command
import plutoproject.feature.paper.api.afk.AfkManager
import plutoproject.framework.paper.util.command.ensurePlayer

@Suppress("UNUSED")
object AfkCommand {
    @Command("afk")
    fun CommandSender.afk() = ensurePlayer {
        AfkManager.toggle(this, true)
    }
}
