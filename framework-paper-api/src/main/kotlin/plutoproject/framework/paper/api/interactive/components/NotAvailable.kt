package plutoproject.framework.paper.api.interactive.components

import androidx.compose.runtime.Composable
import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.italic
import ink.pmc.advkt.component.text
import net.kyori.adventure.text.Component
import org.bukkit.Material
import plutoproject.framework.common.util.chat.palettes.MOCHA_MAROON
import plutoproject.framework.common.util.chat.palettes.MOCHA_SUBTEXT_0

@Suppress("FunctionName")
@Composable
fun NotAvailable(
    material: Material,
    name: Component
) {
    Item(
        material = material,
        name = name,
        lore = buildList {
            add(component {
                text("因服务器内部问题，此功能不可用") with MOCHA_MAROON without italic()
            })
            add(component {
                text("请将其反馈给管理组以便我们尽快解决") with MOCHA_SUBTEXT_0 without italic()
            })
        }
    )
}
