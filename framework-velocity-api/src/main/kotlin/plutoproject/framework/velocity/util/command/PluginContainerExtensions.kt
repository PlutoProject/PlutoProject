package plutoproject.framework.velocity.util.command

import com.github.shynixn.mccoroutine.velocity.SuspendingPluginContainer
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.plugin.PluginContainer
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.velocity.VelocityCommandManager
import plutoproject.framework.velocity.util.server

val PluginContainer.commandManager: VelocityCommandManager<CommandSource>
    get() = VelocityCommandManager(
        this,
        server,
        ExecutionCoordinator.asyncCoordinator(),
        SenderMapper.identity()
    ).apply {
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

val SuspendingPluginContainer.commandManager: VelocityCommandManager<CommandSource>
    get() = pluginContainer.commandManager
