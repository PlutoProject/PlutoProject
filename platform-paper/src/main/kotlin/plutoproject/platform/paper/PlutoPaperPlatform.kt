package plutoproject.platform.paper

import org.bukkit.plugin.java.JavaPlugin
import plutoproject.framework.common.util.coroutine.shutdownCoroutineEnvironment
import plutoproject.framework.paper.util.hook.initHooks
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.serverThread
import plutoproject.framework.paper.util.server as utilServer

@Suppress("UNUSED")
class PlutoPaperPlatform : JavaPlugin() {
    override fun onLoad() {
        plugin = this
        utilServer = server
        serverThread = Thread.currentThread()
    }

    override fun onEnable() {
        initHooks()
    }

    override fun onDisable() {
        shutdownCoroutineEnvironment()
    }
}
