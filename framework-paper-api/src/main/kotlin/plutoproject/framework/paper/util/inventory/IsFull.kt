package plutoproject.framework.paper.util.inventory

import org.bukkit.inventory.PlayerInventory

val PlayerInventory.isFull: Boolean
    get() = !storageContents.contains(null)
