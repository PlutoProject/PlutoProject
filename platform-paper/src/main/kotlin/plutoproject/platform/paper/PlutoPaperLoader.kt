package plutoproject.platform.paper

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import plutoproject.framework.common.dependency.PaperDependencyResolver
import xyz.jpenilla.gremlin.runtime.platformsupport.PaperClasspathAppender

@Suppress("UNUSED")
class PlutoPaperLoader : PluginLoader {
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        val cachePath = classpathBuilder.getContext().getDataDirectory().resolve("libraries")
        val cacheFile = cachePath.toFile()
        if (!cacheFile.exists()) {
            cacheFile.mkdirs()
        }
        PaperClasspathAppender(classpathBuilder).append(PaperDependencyResolver.resolve(cachePath))
    }
}