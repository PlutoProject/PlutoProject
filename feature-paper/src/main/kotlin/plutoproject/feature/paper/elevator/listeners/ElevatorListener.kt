package plutoproject.feature.paper.elevator.listeners

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent
import plutoproject.feature.paper.elevator.handlePlayerJumpFloorUp
import plutoproject.feature.paper.elevator.handlePlayerSneakFloorDown

object ElevatorListener : Listener {
    @EventHandler
    suspend fun PlayerJumpEvent.e() {
        handlePlayerJumpFloorUp(this)
    }

    @EventHandler
    suspend fun PlayerToggleSneakEvent.e() {
        handlePlayerSneakFloorDown(this)
    }
}
