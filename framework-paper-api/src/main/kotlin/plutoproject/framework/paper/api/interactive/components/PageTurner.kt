package plutoproject.framework.paper.api.interactive.components

import androidx.compose.runtime.Composable
import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.italic
import ink.pmc.advkt.component.text
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType
import plutoproject.framework.common.util.chat.SoundConstants
import plutoproject.framework.common.util.chat.palettes.MOCHA_LAVENDER
import plutoproject.framework.common.util.chat.palettes.MOCHA_TEXT
import plutoproject.framework.paper.api.interactive.LocalPlayer
import plutoproject.framework.paper.api.interactive.modifiers.Modifier
import plutoproject.framework.paper.api.interactive.click.clickable
import plutoproject.framework.paper.api.interactive.components.SeparatePageTunerMode.NEXT
import plutoproject.framework.paper.api.interactive.components.SeparatePageTunerMode.PREVIOUS

enum class SeparatePageTunerMode {
    PREVIOUS, NEXT
}

@Suppress("UNUSED", "FunctionName")
@Composable
fun SeparatePageTuner(
    icon: Material = Material.ARROW,
    description: Collection<Component> = emptyList(),
    mode: SeparatePageTunerMode,
    current: Int,
    total: Int,
    turn: suspend () -> Boolean
) {
    val player = LocalPlayer.current
    Item(
        material = icon,
        name = component {
            text("第 $current/$total 页") with MOCHA_TEXT without italic()
        },
        lore = buildList {
            addAll(description)
            add(Component.empty())
            add(component {
                when (mode) {
                    PREVIOUS -> {
                        text("左键 ") with MOCHA_LAVENDER without italic()
                        text("上一页") with MOCHA_TEXT without italic()
                    }

                    NEXT -> {
                        text("左键 ") with MOCHA_LAVENDER without italic()
                        text("下一页") with MOCHA_TEXT without italic()
                    }
                }
            })
        },
        modifier = Modifier.clickable {
            if (clickType != ClickType.LEFT) {
                return@clickable
            }
            if (turn()) player.playSound(SoundConstants.UI.paging)
        }
    )
}
