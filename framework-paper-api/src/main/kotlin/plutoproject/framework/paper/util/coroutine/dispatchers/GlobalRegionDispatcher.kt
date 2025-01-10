package plutoproject.framework.paper.util.coroutine.dispatchers

import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler
import kotlinx.coroutines.CoroutineDispatcher
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server
import kotlin.coroutines.CoroutineContext

/**
 * 基于 Folia [GlobalRegionScheduler] 的 [CoroutineDispatcher]。
 */
object GlobalRegionDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        server.globalRegionScheduler.execute(plugin, block)
    }
}
