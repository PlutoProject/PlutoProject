package plutoproject.feature.paper.creeperAntiExplode

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

object ExplodeListener : Listener {
    @EventHandler
    fun EntityExplodeEvent.e() {
        if (entity.type != EntityType.CREEPER) return
        blockList().clear()
    }
}
