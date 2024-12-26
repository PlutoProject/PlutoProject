package plutoproject.framework.common.util.chat.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.TextColor

fun Component.replace(string: String, str: String): Component {
    val replaceConfig = TextReplacementConfig.builder()
        .match(string)
        .replacement(Component.text(str))
        .build()
    return this.replaceText(replaceConfig)
}

fun Component.replace(pattern: String, component: Component): Component {
    val replaceConfig = TextReplacementConfig.builder()
        .match(pattern)
        .replacement(component)
        .build()
    return replaceText(replaceConfig)
}

fun Component.replace(string: String, any: Any?): Component {
    return replace(string, any.toString())
}

fun Iterable<Component>.replace(pattern: String, str: String): Iterable<Component> {
    return map { it.replace(pattern, str) }
}

fun Iterable<Component>.replace(pattern: String, component: Component): Iterable<Component> {
    return map { it.replace(pattern, component) }
}

fun Iterable<Component>.replace(pattern: String, any: Any?): Iterable<Component> {
    return map { it.replace(pattern, any) }
}

fun Component.replaceColor(original: TextColor, new: TextColor): Component {
    val updatedComponent =
        if (this.style().color() == original) {
            this.style(this.style().color(new))
        } else {
            this
        }
    return updatedComponent
        .children()
        .fold(updatedComponent.children(emptyList())) { comp, child ->
            comp.append(child.replaceColor(original, new))
        }
}