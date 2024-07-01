package ink.pmc.utils.dsl.invui.item

import ink.pmc.utils.dsl.ItemStackDsl
import ink.pmc.utils.structure.Builder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.item.Click
import xyz.xenondevs.invui.item.Item
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import kotlin.coroutines.coroutineContext

typealias ClickHandler = (click: Click) -> Unit
typealias SuspendClickHandler = suspend (click: Click) -> Unit

abstract class ItemDsl<T : Item> : Builder<T> {

    lateinit var item: Item
    var itemProvider = ItemProvider.EMPTY
    protected var clickHandler: ClickHandler? = null

    fun provider(itemStack: ItemStack) {
        itemProvider = ItemBuilder(itemStack)
    }

    fun provider(material: Material, amount: Int = 1, itemStack: ItemStackDsl.() -> Unit) {
        itemProvider = ItemBuilder(ink.pmc.utils.dsl.itemStack(material, amount, itemStack))
    }

    fun onClick(handler: ClickHandler) {
        clickHandler = handler
    }

    suspend fun onClickSuspending(handler: SuspendClickHandler) {
        val context = coroutineContext
        clickHandler = { CoroutineScope(context).launch(context, CoroutineStart.UNDISPATCHED) { handler(it) } }
    }

    fun notifyWindows() {
        item.notifyWindows()
    }

    abstract override fun build(): T

}