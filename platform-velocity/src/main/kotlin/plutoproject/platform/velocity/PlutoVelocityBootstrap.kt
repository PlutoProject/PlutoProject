package plutoproject.platform.velocity

import com.github.shynixn.mccoroutine.velocity.SuspendingPluginContainer
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.proxy.plugin.PluginClassLoader
import plutoproject.framework.common.dependency.VelocityDependencyResolver
import java.nio.file.Path
import java.util.logging.Logger

@Suppress("UNUSED")
class PlutoVelocityBootstrap @Inject constructor(spc: SuspendingPluginContainer) {
    private lateinit var platform: PlutoVelocityPlatform

    init {
        spc.initialize(this)
    }

    @Inject
    fun plutoVelocity(server: ProxyServer, logger: Logger, @DataDirectory dataDirectoryPath: Path) {
        loadDependencies(dataDirectoryPath.getCachePath())
        platform = PlutoVelocityPlatform()
        platform.load(server, logger, dataDirectoryPath)
    }

    private fun loadDependencies(cachePath: Path) {
        val addPathMethod = PluginClassLoader::class.java
            .getDeclaredMethod("addPath", Path::class.java)
            .apply { isAccessible = true }
        val classLoader = this::class.java.classLoader as PluginClassLoader
        val dependencies = VelocityDependencyResolver.resolve(cachePath)
        dependencies.forEach {
            addPathMethod.invoke(classLoader, it)
        }
    }

    private fun Path.getCachePath(): Path {
        val cachePath = resolve("libraries")
        val cacheDir = cachePath.toFile()
        if (!(cacheDir.exists())) {
            cacheDir.mkdirs()
        }
        return cachePath
    }

    @Subscribe
    fun ProxyInitializeEvent.e() {
        platform.enable()
    }

    @Subscribe
    fun ProxyShutdownEvent.e() {
        platform.disable()
    }
}
