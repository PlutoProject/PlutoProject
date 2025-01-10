package plutoproject.framework.paper.util.coroutine.dispatchers

import io.papermc.paper.threadedregions.scheduler.EntityScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.bukkit.entity.Entity
import plutoproject.framework.paper.util.plugin
import kotlin.coroutines.CoroutineContext

/**
 * 基于 Folia [EntityScheduler] 的 [CoroutineDispatcher]。
 */
class EntityDispatcher(private val entity: Entity) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        entity.scheduler.execute(plugin, block, {}, 0L)
    }
}
