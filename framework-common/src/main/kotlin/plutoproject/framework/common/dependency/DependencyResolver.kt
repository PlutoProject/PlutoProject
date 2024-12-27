package plutoproject.framework.common.dependency

import org.slf4j.LoggerFactory
import xyz.jpenilla.gremlin.runtime.DependencyCache
import xyz.jpenilla.gremlin.runtime.DependencyResolver
import xyz.jpenilla.gremlin.runtime.DependencySet
import java.nio.file.Path

interface DependencyResolver {
    val dependenciesFileName: String

    fun resolve(cacheDir: Path): Set<Path> {
        val deps = DependencySet.readFromClasspathResource(
            DependencyResolver::class.java.classLoader, dependenciesFileName
        )
        val cache = DependencyCache(cacheDir)
        val logger = LoggerFactory.getLogger("framework/common/api/DependencyResolver")
        val files: Set<Path>
        DependencyResolver(logger).use { downloader ->
            files = downloader.resolve(deps, cache).jarFiles()
        }
        cache.cleanup()
        return files
    }
}