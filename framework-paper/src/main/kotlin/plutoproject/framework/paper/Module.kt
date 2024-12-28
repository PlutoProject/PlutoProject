package plutoproject.framework.paper

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import net.kyori.adventure.text.minimessage.MiniMessage
import org.incendo.cloud.minecraft.extras.parser.ComponentParser
import org.incendo.cloud.parser.standard.StringParser
import plutoproject.framework.common.api.bridge.Bridge
import plutoproject.framework.common.api.provider.Provider
import plutoproject.framework.common.api.rpc.RpcClient
import plutoproject.framework.common.util.command.annotationParser
import plutoproject.framework.paper.api.interactive.GuiManager
import plutoproject.framework.paper.bridge.BridgeCommand
import plutoproject.framework.paper.bridge.listeners.BridgePlayerListener
import plutoproject.framework.paper.bridge.listeners.BridgeWorldListener
import plutoproject.framework.paper.bridge.startBridgeBackgroundTask
import plutoproject.framework.paper.bridge.stopBridgeBackgroundTask
import plutoproject.framework.paper.interactive.GuiListener
import plutoproject.framework.paper.interactive.commands.InteractiveCommand
import plutoproject.framework.paper.interactive.inventory.InventoryListener
import plutoproject.framework.paper.options.PaperOptionsListener
import plutoproject.framework.paper.options.startOptionsMonitor
import plutoproject.framework.paper.options.stopOptionsMonitor
import plutoproject.framework.paper.playerdb.startPlayerDBMonitor
import plutoproject.framework.paper.playerdb.stopPlayerDBMonitor
import plutoproject.framework.paper.util.command.commandManager
import plutoproject.framework.paper.util.hook.initHooks
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

fun loadFrameworkModules() {
    RpcClient.start()
    Provider
}

fun enableFrameworkModules() {
    initHooks()
    registerListeners()
    configureCommands()
    Bridge
    startOptionsMonitor()
    startPlayerDBMonitor()
    startBridgeBackgroundTask()
}

private fun registerListeners() = server.pluginManager.apply {
    registerSuspendingEvents(GuiListener, plugin)
    registerSuspendingEvents(InventoryListener, plugin)
    registerSuspendingEvents(PaperOptionsListener, plugin)
    registerSuspendingEvents(BridgePlayerListener, plugin)
    registerSuspendingEvents(BridgeWorldListener, plugin)
}

private fun configureCommands() {
    plugin.commandManager.apply {
        parserRegistry().apply {
            registerNamedParser(
                "bridge-component",
                ComponentParser.componentParser(MiniMessage.miniMessage(), StringParser.StringMode.QUOTED)
            )
        }
    }.annotationParser.apply {
        parse(InteractiveCommand)
        parse(BridgeCommand)
    }
}

fun disableFrameworkModules() {
    stopOptionsMonitor()
    stopPlayerDBMonitor()
    stopBridgeBackgroundTask()
    GuiManager.disposeAll()
    Provider.close()
    RpcClient.stop()
}
