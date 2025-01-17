package plutoproject.feature.paper.api.randomTeleport

import org.bukkit.Chunk
import org.bukkit.World
import plutoproject.feature.paper.api.teleport.TaskState
import java.util.*

interface CacheTask {
    val id: UUID
    val world: World
    val options: RandomTeleportOptions
    val attempts: Int
    val cached: Collection<Chunk>
    val state: TaskState
    val isPending: Boolean
    val isTicking: Boolean
    val isFinished: Boolean

    suspend fun tick(): RandomTeleportCache?

    fun cancel()
}
