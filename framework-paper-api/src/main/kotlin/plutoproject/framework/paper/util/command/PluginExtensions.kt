package plutoproject.framework.paper.util.command

import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.LegacyPaperCommandManager
import plutoproject.framework.common.api.bridge.command.parsers.BridgePlayerNotFoundException
import plutoproject.framework.common.api.bridge.command.parsers.BridgeServerNotFoundException
import plutoproject.framework.common.api.bridge.command.parsers.bridgePlayerParser
import plutoproject.framework.common.api.bridge.command.parsers.bridgeServerParser
import plutoproject.framework.paper.api.bridge.command.handlers.BridgePlayerNotFoundHandler
import plutoproject.framework.paper.api.bridge.command.handlers.BridgeServerNotFoundHandler

private lateinit var lateCommandManager: LegacyPaperCommandManager<CommandSender>

val Plugin.commandManager: LegacyPaperCommandManager<CommandSender>
    get() {
        if (!::lateCommandManager.isInitialized) {
            lateCommandManager = LegacyPaperCommandManager.createNative(
                this,
                ExecutionCoordinator.asyncCoordinator()
            ).apply {
                registerBrigadier()
                parserRegistry().apply {
                    registerParser(bridgePlayerParser())
                    registerParser(bridgeServerParser())
                }
                exceptionController().apply {
                    registerHandler(BridgePlayerNotFoundException::class.java, BridgePlayerNotFoundHandler)
                    registerHandler(BridgeServerNotFoundException::class.java, BridgeServerNotFoundHandler)
                }
            }
        }
        return lateCommandManager
    }
