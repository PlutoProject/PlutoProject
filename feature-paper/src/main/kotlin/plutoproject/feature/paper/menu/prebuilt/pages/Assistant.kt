package plutoproject.feature.paper.menu.prebuilt.pages

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.text
import org.bukkit.Material
import plutoproject.feature.paper.api.menu.dsl.PageDescriptor
import plutoproject.framework.common.util.chat.palettes.mochaText

val AssistantPageDescriptor = PageDescriptor {
    id = "menu:assistant"
    icon = Material.TRIPWIRE_HOOK
    name = component {
        text("辅助功能") with mochaText
    }
}
