package plutoproject.feature.paper.elevator

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

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
