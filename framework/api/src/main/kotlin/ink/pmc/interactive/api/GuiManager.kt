package ink.pmc.interactive.api

import ink.pmc.framework.utils.inject.inlinedGet
import ink.pmc.interactive.api.inventory.layout.InventoryNode
import org.bukkit.entity.Player

typealias GuiInventoryScope = GuiScope<InventoryNode>

interface GuiManager {

    companion object : GuiManager by inlinedGet()

    fun get(player: Player): GuiScope<*>?

    fun getInventory(player: Player): GuiInventoryScope?

    fun has(player: Player): Boolean

    fun hasInventory(player: Player): Boolean

    fun startInventory(player: Player, contents: ComposableFunction): GuiInventoryScope

    fun removeScope(scope: GuiScope<*>)

    fun dispose(player: Player)

    fun disposeAll()

}