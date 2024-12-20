package plutoproject.platform.velocity

import com.google.common.eventbus.Subscribe
import com.google.inject.Inject
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.proxy.plugin.PluginClassLoader
import kotlinx.coroutines.runBlocking
import plutoproject.framework.common.api.dependency.VelocityDependencyResolver
import java.nio.file.Path
import java.util.logging.Logger

@Suppress("UNUSED")
class PlutoVelocityPlatform {
    @Inject
    fun plutoVelocity(server: ProxyServer, logger: Logger, @DataDirectory dataDirectoryPath: Path) {
        loadDependencies(dataDirectoryPath.getCachePath())
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
    fun ProxyInitializeEvent.e() = runBlocking {
    }
}