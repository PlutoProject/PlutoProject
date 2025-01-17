package plutoproject.feature.paper.randomTeleport

import kotlinx.coroutines.*
import org.bukkit.Chunk
import org.bukkit.World
import org.koin.core.component.KoinComponent
import plutoproject.feature.paper.api.randomTeleport.CacheTask
import plutoproject.feature.paper.api.randomTeleport.RandomTeleportCache
import plutoproject.feature.paper.api.randomTeleport.RandomTeleportManager
import plutoproject.feature.paper.api.randomTeleport.RandomTeleportOptions
import plutoproject.feature.paper.api.teleport.TaskState
import plutoproject.feature.paper.api.teleport.TeleportManager
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class CacheTaskImpl(
    override val world: World,
    override val options: RandomTeleportOptions,
) : CacheTask, KoinComponent {
    private var scope: CoroutineScope? = null

    override val id: UUID = UUID.randomUUID()
    override var attempts: Int = 0
    override val cached: MutableSet<Chunk> = ConcurrentHashMap.newKeySet()
    override var state: TaskState = TaskState.PENDING
    override val isPending: Boolean
        get() = state == TaskState.PENDING
    override val isTicking: Boolean
        get() = state == TaskState.TICKING
    override val isFinished: Boolean
        get() = state == TaskState.FINISHED

    override suspend fun tick(): RandomTeleportCache? {
        if (isTicking || isFinished) {
            return null
        }

        return supervisorScope {
            state = TaskState.TICKING
            scope = this
            val random = RandomTeleportManager.random(world, options)
            val location = random.location ?: return@supervisorScope null
            val chunks = TeleportManager.getRequiredChunks(
                location,
                RandomTeleportManager.getRandomTeleportOptions(world).chunkPreserveRadius
            )

            chunks.forEach {
                launch {
                    val chunk = it.coordinateChunkIn(location.world)
                    chunk.addTeleportTicket()
                    cached.add(chunk)
                    yield()
                }
            }

            state = TaskState.FINISHED
            RandomTeleportCache(
                id = id,
                world = location.world,
                center = location.chunk,
                preservedChunks = cached,
                attempts = random.attempts,
                location = location,
                options = options
            )
        }
    }

    override fun cancel() {
        scope?.cancel()
        state = TaskState.FINISHED
        cached.forEach { it.removeTeleportTicket() }
    }
}
