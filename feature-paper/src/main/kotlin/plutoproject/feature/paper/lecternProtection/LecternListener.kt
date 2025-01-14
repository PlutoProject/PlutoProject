package plutoproject.feature.paper.lecternProtection

import org.bukkit.block.Lectern
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerTakeLecternBookEvent
import plutoproject.feature.paper.itemFrameProtection.isOpenableBook
import plutoproject.framework.common.util.chat.component.replace

@Suppress("UNUSED")
object LecternListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun PlayerTakeLecternBookEvent.e() {
        if (!lectern.isProtected) return
        if (lectern.protector == player || player.hasPermission(LECTERN_PROTECTION_BYPASS_PERMISSION)) return
        player.sendActionBar(LECTERN_PROTECTED_ON_ACTION.replace("<player>", lectern.protectorName))
        player.closeInventory()
        isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun PlayerInteractEvent.e() {
        val state = clickedBlock?.state

        if (state !is Lectern) return
        if (hand == null) return
        if (!player.inventory.getItem(hand!!).type.isOpenableBook) return
        if (!state.inventory.isEmpty) return
        if (!state.isProtected) return
        if (state.protector == player || player.hasPermission(LECTERN_PROTECTION_BYPASS_PERMISSION)) return

        player.sendActionBar(LECTERN_PROTECTED_ON_ACTION.replace("<player>", state.protectorName))
        isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun BlockBreakEvent.e() {
        val state = block.state

        if (state !is Lectern) return
        if (!state.isProtected) return
        if (state.protector == player || player.hasPermission(LECTERN_PROTECTION_BYPASS_PERMISSION)) return

        player.sendActionBar(LECTERN_PROTECTED_ON_ACTION.replace("<player>", state.protectorName))
        isCancelled = true
    }
}
