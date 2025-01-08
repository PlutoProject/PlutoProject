package plutoproject.platform.paper

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import plutoproject.framework.common.FrameworkCommonModule
import plutoproject.framework.common.api.feature.FeatureManager
import plutoproject.framework.common.util.PlatformType
import plutoproject.framework.common.util.coroutine.shutdownCoroutineEnvironment
import plutoproject.framework.common.util.initPluginDataFolder
import plutoproject.framework.common.util.inject.configureKoin
import plutoproject.framework.common.util.jvm.loadClassesInPackages
import plutoproject.framework.common.util.platformType
import plutoproject.framework.common.util.serverThread
import plutoproject.framework.paper.FrameworkPaperModule
import plutoproject.framework.paper.disableFrameworkModules
import plutoproject.framework.paper.enableFrameworkModules
import plutoproject.framework.paper.loadFrameworkModules
import plutoproject.framework.paper.util.plugin
import kotlin.system.measureTimeMillis
import plutoproject.framework.common.util.logger as utilLogger
import plutoproject.framework.paper.util.server as utilServer

@Suppress("UNUSED")
class PlutoPaperPlatform : SuspendingJavaPlugin() {
    override fun onLoad() {
        plugin = this
        utilServer = server
        utilLogger = logger
        platformType = PlatformType.PAPER
        serverThread = Thread.currentThread()
        dataFolder.initPluginDataFolder()
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        preload()
        configureKoin {
            modules(FrameworkCommonModule, FrameworkPaperModule)
        }
        loadFrameworkModules()
        FeatureManager.loadAll()
    }

    private fun preload() {
        logger.info("Preloading resources to improve runtime performance...")
        val time = measureTimeMillis {
            loadClassesInPackages(
                "androidx",
                "cafe.adriel.voyager",
                "plutoproject.framework.common",
                "plutoproject.framework.paper",
                "plutoproject.feature.common",
                "plutoproject.feature.paper",
                classLoader = PlutoPaperPlatform::class.java.classLoader
            )
        }
        logger.info("Resource preloaded in ${time}ms")
    }

    override fun onEnable() {
        enableFrameworkModules()
        FeatureManager.enableAll()
    }

    override fun onDisable() {
        FeatureManager.disableAll()
        disableFrameworkModules()
        shutdownCoroutineEnvironment()
    }
}
