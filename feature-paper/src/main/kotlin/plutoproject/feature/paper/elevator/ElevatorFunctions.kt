package plutoproject.feature.paper.elevator

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import plutoproject.feature.paper.api.elevator.ElevatorManager
import plutoproject.framework.paper.util.coroutine.withSync

suspend fun handlePlayerJumpFloorUp(event: PlayerJumpEvent) {
    val player = event.player
    player.withSync {
        val chain = ElevatorManager.getChainAt(event.from) ?: return@withSync
        chain.up(player)
    }
}

suspend fun handlePlayerSneakFloorDown(event: PlayerToggleSneakEvent) {
    if (!event.isSneaking) return
    val player = event.player
    player.withSync {
        val chain = ElevatorManager.getChainAt(player.location) ?: return@withSync
        chain.down(player)
    }
}
