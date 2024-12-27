package plutoproject.framework.paper.api.interactive.components

import androidx.compose.runtime.*
import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.italic
import ink.pmc.advkt.component.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType
import plutoproject.framework.common.util.chat.SoundConstants
import plutoproject.framework.common.util.chat.palettes.MOCHA_LAVENDER
import plutoproject.framework.common.util.chat.palettes.MOCHA_SUBTEXT_0
import plutoproject.framework.common.util.chat.palettes.MOCHA_TEXT
import plutoproject.framework.common.util.chat.palettes.MOCHA_YELLOW
import plutoproject.framework.paper.api.interactive.LocalPlayer
import plutoproject.framework.paper.api.interactive.modifiers.Modifier
import plutoproject.framework.paper.api.interactive.click.clickable

@Suppress("UNUSED", "FunctionName")
@Composable
fun Selector(
    title: Component,
    icon: Material = Material.STICK,
    description: List<Component> = emptyList(),
    options: List<String>,
    default: Int = 0,
    highlightColor: TextColor = MOCHA_YELLOW,
    goNext: suspend () -> Unit,
    goPrevious: suspend () -> Unit
) {
    require(options.isNotEmpty()) { "Options cannot be empty" }
    val player = LocalPlayer.current
    var current by remember { mutableStateOf(default) }
    Item(
        name = title,
        material = icon,
        lore = buildList {
            addAll(description)
            addAll(options.mapIndexed { index, s ->
                component {
                    text("» $s") with (if (current == index) highlightColor else MOCHA_SUBTEXT_0) without italic()
                }
            })
            add(Component.empty())
            add(component {
                text("左键 ") with MOCHA_LAVENDER without italic()
                text("向后切换") with MOCHA_TEXT without italic()
            })
            add(component {
                text("右键 ") with MOCHA_LAVENDER without italic()
                text("向前切换") with MOCHA_TEXT without italic()
            })
        },
        modifier = Modifier.clickable {
            when (clickType) {
                ClickType.LEFT -> {
                    goNext()
                    current = if (current == options.lastIndex) 0 else current + 1
                    player.playSound(SoundConstants.UI.selector)
                }

                ClickType.RIGHT -> {
                    goPrevious()
                    current = if (current == 0) options.lastIndex else current - 1
                    player.playSound(SoundConstants.UI.selector)
                }

                else -> {}
            }
        }
    )
}
