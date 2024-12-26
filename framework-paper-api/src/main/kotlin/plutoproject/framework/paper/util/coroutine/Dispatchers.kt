package plutoproject.framework.paper.util.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.entity.Entity
import plutoproject.framework.paper.util.PLUGIN
import plutoproject.framework.paper.util.SERVER
import plutoproject.framework.paper.util.isFoliaEnvironment
import plutoproject.framework.paper.util.toNms
import kotlin.coroutines.CoroutineContext

private val GLOBAL_REGION_DISPATCHER = object : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        SERVER.globalRegionScheduler.execute(PLUGIN, block)
    }
}

val Server.coroutineContext: CoroutineContext
    get() = if (isFoliaEnvironment) {
        GLOBAL_REGION_DISPATCHER
    } else {
        SERVER.toNms().asCoroutineDispatcher()
    }

val Entity.coroutineContext: CoroutineContext
    get() = if (isFoliaEnvironment) {
        object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                this@coroutineContext.scheduler.execute(PLUGIN, block, {}, 0L)
            }
        }
    } else {
        coroutineContext
    }

val Chunk.coroutineContext: CoroutineContext
    get() = if (isFoliaEnvironment) {
        object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                val chunk = this@coroutineContext
                SERVER.regionScheduler.execute(PLUGIN, chunk.world, chunk.x, chunk.z, block)
            }
        }
    } else {
        coroutineContext
    }

val Location.coroutineContext
    get() = chunk.coroutineContext
