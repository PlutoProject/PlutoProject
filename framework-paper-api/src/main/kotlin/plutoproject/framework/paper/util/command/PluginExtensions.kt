package plutoproject.framework.paper.util.command

import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.LegacyPaperCommandManager

val Plugin.commandManager: LegacyPaperCommandManager<CommandSender>
    get() = LegacyPaperCommandManager.createNative(
        this,
        ExecutionCoordinator.asyncCoordinator()
    ).apply {
        registerBrigadier()
        // TODO: make this work
        /*
        parserRegistry().apply {
            registerParser(bridgePlayerParser())
            registerParser(bridgeServerParser())
        }
        exceptionController().apply {
            registerHandler(BridgePlayerNotFoundException::class.java, BridgePlayerNotFoundHandler)
            registerHandler(BridgeServerNotFoundException::class.java, BridgeServerNotFoundHandler)
        }
        */
    }
