package plutoproject.framework.common.util.chat.component

import net.kyori.adventure.text.Component

fun Component.splitLines(): Iterable<Component> {
    var curr = Component.empty()
    return buildList {
        val root = this@splitLines.children(emptyList())
        if (root != Component.empty()) {
            add(root)
        }
        children().forEach { child ->
            if (child == Component.newline()) {
                if (curr != Component.empty()) {
                    add(curr)
                }
                curr = Component.empty()
                return@forEach
            }
            curr = curr.append(child)
        }
        if (curr != Component.empty()) {
            add(curr)
        }
    }
}