package plutoproject.framework.paper.util.coroutine.dispatchers

import io.papermc.paper.threadedregions.scheduler.RegionScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.Chunk
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server
import kotlin.coroutines.CoroutineContext

/**
 * 基于 Folia [RegionScheduler] 的 [CoroutineDispatcher]。
 */
class ChunkDispatcher(private val chunk: Chunk) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        server.regionScheduler.execute(plugin, chunk.world, chunk.x, chunk.z, block)
    }
}
