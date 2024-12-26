package plutoproject.platform.paper

import org.bukkit.plugin.java.JavaPlugin
import plutoproject.framework.common.util.coroutine.shutdownCoroutineEnvironment
import plutoproject.framework.paper.util.THREAD

@Suppress("UNUSED")
class PlutoPaperPlatform : JavaPlugin() {
    override fun onLoad() {
        THREAD = Thread.currentThread()
    }

    override fun onEnable() {
    }

    override fun onDisable() {
        shutdownCoroutineEnvironment()
    }
}