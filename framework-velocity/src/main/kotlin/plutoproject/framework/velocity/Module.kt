package plutoproject.framework.velocity

import com.github.shynixn.mccoroutine.velocity.registerSuspend
import net.kyori.adventure.text.minimessage.MiniMessage
import org.incendo.cloud.minecraft.extras.parser.ComponentParser
import org.incendo.cloud.parser.standard.StringParser
import plutoproject.framework.common.api.bridge.Bridge
import plutoproject.framework.common.api.provider.Provider
import plutoproject.framework.common.api.rpc.RpcServer
import plutoproject.framework.common.util.command.annotationParser
import plutoproject.framework.velocity.bridge.BridgeCommand
import plutoproject.framework.velocity.bridge.BridgePlayerListener
import plutoproject.framework.velocity.bridge.BridgeRpc
import plutoproject.framework.velocity.options.OptionsListener
import plutoproject.framework.velocity.options.proto.OptionsRpc
import plutoproject.framework.velocity.playerdb.proto.PlayerDBRpc
import plutoproject.framework.velocity.profile.ProfileCacheListener
import plutoproject.framework.velocity.rpc.RpcCommand
import plutoproject.framework.velocity.util.command.commandManager
import plutoproject.framework.velocity.util.plugin
import plutoproject.framework.velocity.util.server

fun loadFrameworkModules() {
    RpcServer.apply {
        addService(OptionsRpc)
        addService(PlayerDBRpc)
        addService(BridgeRpc)
    }
    Provider
}

private fun registerListeners() = server.eventManager.apply {
    registerSuspend(plugin, OptionsListener)
    registerSuspend(plugin, BridgePlayerListener)
    registerSuspend(plugin, ProfileCacheListener)
}

private fun configureCommands() {
    registerListeners()
    plugin.commandManager.apply {
        parserRegistry().apply {
            registerNamedParser(
                "bridge-component",
                ComponentParser.componentParser(MiniMessage.miniMessage(), StringParser.StringMode.QUOTED)
            )
        }
    }.annotationParser.apply {
        parse(RpcCommand)
        parse(BridgeCommand)
    }
}

fun enableFrameworkModules() {
    configureCommands()
    RpcServer.start()
    Bridge
}

fun disableFrameworkModules() {
    Provider.close()
    RpcServer.stop()
}
