package plutoproject.feature.paper.menu

import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Command
import plutoproject.feature.paper.menu.screens.MenuScreen
import plutoproject.framework.paper.api.interactive.startScreen
import plutoproject.framework.paper.util.command.ensurePlayer

@Suppress("UNUSED")
object MenuCommand {
    @Command("menu")
    fun CommandSender.menu() = ensurePlayer {
        startScreen(MenuScreen())
    }
}
