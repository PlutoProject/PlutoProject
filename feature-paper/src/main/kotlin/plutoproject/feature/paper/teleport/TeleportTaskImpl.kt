package plutoproject.feature.paper.teleport

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.supervisorScope
import org.bukkit.Location
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent
import plutoproject.feature.paper.api.teleport.TeleportManager
import plutoproject.feature.paper.api.teleport.TeleportOptions
import plutoproject.feature.paper.api.teleport.TeleportTask
import plutoproject.feature.paper.api.teleport.TeleportTaskState
import plutoproject.framework.paper.util.world.chunk.ChunkLocation
import java.util.*

class TeleportTaskImpl(
    override val id: UUID,
    override val player: Player,
    override val destination: Location,
    override val teleportOptions: TeleportOptions?,
    override val prompt: Boolean,
    override val chunkNeedToPrepare: List<ChunkLocation>,
) : TeleportTask, KoinComponent {
    private var scope: CoroutineScope? = null

    override var state: TeleportTaskState = TeleportTaskState.PENDING
    override val isPending: Boolean
        get() = state == TeleportTaskState.PENDING
    override val isTicking: Boolean
        get() = state == TeleportTaskState.TICKING
    override val isFinished: Boolean
        get() = state == TeleportTaskState.FINISHED

    override suspend fun tick() {
        if (isTicking || isFinished) {
            return
        }

        state = TeleportTaskState.TICKING
        supervisorScope {
            scope = this
            TeleportManager.prepareChunk(chunkNeedToPrepare, destination.world)
            TeleportManager.fireTeleport(player, destination, teleportOptions, prompt)
        }
        state = TeleportTaskState.FINISHED
    }

    override fun cancel() {
        if (isFinished || scope == null) {
            return
        }
        scope?.cancel()
        player.clearTitle()
    }
}
