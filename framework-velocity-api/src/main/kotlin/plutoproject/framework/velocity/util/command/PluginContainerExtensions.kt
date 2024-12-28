package plutoproject.framework.velocity.util.command

import com.github.shynixn.mccoroutine.velocity.SuspendingPluginContainer
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.plugin.PluginContainer
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.velocity.VelocityCommandManager
import plutoproject.framework.common.api.bridge.command.parsers.BridgePlayerNotFoundException
import plutoproject.framework.common.api.bridge.command.parsers.BridgeServerNotFoundException
import plutoproject.framework.common.api.bridge.command.parsers.bridgePlayerParser
import plutoproject.framework.common.api.bridge.command.parsers.bridgeServerParser
import plutoproject.framework.velocity.api.bridge.command.handlers.BridgePlayerNotFoundHandler
import plutoproject.framework.velocity.api.bridge.command.handlers.BridgeServerNotFoundHandler
import plutoproject.framework.velocity.util.server

private lateinit var lateCommandManager: VelocityCommandManager<CommandSource>

val PluginContainer.commandManager: VelocityCommandManager<CommandSource>
    get() {
        if (!::lateCommandManager.isInitialized) {
            lateCommandManager = VelocityCommandManager(
                this,
                server,
                ExecutionCoordinator.asyncCoordinator(),
                SenderMapper.identity()
            ).apply {
                // TODO: make this work
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

val SuspendingPluginContainer.commandManager: VelocityCommandManager<CommandSource>
    get() = pluginContainer.commandManager
