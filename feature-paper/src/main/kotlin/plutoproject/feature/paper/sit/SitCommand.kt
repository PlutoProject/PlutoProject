package plutoproject.feature.paper.sit

import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Command
import plutoproject.feature.paper.api.sit.SitManager
import plutoproject.framework.paper.util.command.ensurePlayer
import plutoproject.framework.paper.util.coroutine.withSync

@Suppress("UNUSED")
object SitCommand {
    @Command("sit")
    suspend fun CommandSender.sit() = ensurePlayer {
        withSync {
            SitManager.sit(this@ensurePlayer, location)
        }
    }
}
