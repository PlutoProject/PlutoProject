package plutoproject.feature.paper.api.teleport

import org.bukkit.Location
import org.bukkit.entity.Player
import plutoproject.framework.paper.util.world.chunk.ChunkLocation
import java.util.*

enum class TeleportTaskState {
    PENDING, TICKING, FINISHED
}

interface TeleportTask {
    val id: UUID
    val player: Player
    val destination: Location
    val teleportOptions: TeleportOptions?
    val prompt: Boolean
    val chunkNeedToPrepare: Collection<ChunkLocation>
    val state: TeleportTaskState
    val isPending: Boolean
    val isTicking: Boolean
    val isFinished: Boolean

    suspend fun tick()

    fun cancel()
}
