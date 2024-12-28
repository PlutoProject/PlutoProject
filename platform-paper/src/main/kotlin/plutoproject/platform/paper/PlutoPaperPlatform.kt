package plutoproject.platform.paper

import org.bukkit.plugin.java.JavaPlugin
import plutoproject.framework.common.FrameworkCommonModule
import plutoproject.framework.common.util.coroutine.shutdownCoroutineEnvironment
import plutoproject.framework.common.util.initPluginDataFolder
import plutoproject.framework.common.util.inject.modifyExistedKoinOrCreate
import plutoproject.framework.common.util.jvm.loadClassesInPackages
import plutoproject.framework.common.util.serverThread
import plutoproject.framework.paper.FrameworkPaperModule
import plutoproject.framework.paper.disableFrameworkModules
import plutoproject.framework.paper.enableFrameworkModules
import plutoproject.framework.paper.loadFrameworkModules
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.common.util.logger as utilLogger
import plutoproject.framework.paper.util.server as utilServer

@Suppress("UNUSED")
class PlutoPaperPlatform : JavaPlugin() {
    override fun onLoad() {
        plugin = this
        utilServer = server
        utilLogger = logger
        serverThread = Thread.currentThread()
        dataFolder.initPluginDataFolder()
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        preload()
        modifyExistedKoinOrCreate {
            modules(FrameworkCommonModule, FrameworkPaperModule)
        }
        loadFrameworkModules()
    }

    private fun preload() = loadClassesInPackages(
        "androidx",
        "cafe.adriel.voyager",
        "plutoproject.framework.common",
        "plutoproject.framework.paper",
        "plutoproject.feature.common",
        "plutoproject.feature.paper",
        classLoader = PlutoPaperPlatform::class.java.classLoader
    )

    override fun onEnable() {
        enableFrameworkModules()
    }

    override fun onDisable() {
        disableFrameworkModules()
        shutdownCoroutineEnvironment()
    }
}
