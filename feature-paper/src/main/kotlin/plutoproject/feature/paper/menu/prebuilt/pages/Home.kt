package plutoproject.feature.paper.menu.prebuilt.pages

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.text
import org.bukkit.Material
import plutoproject.feature.paper.api.menu.dsl.PageDescriptor
import plutoproject.framework.common.util.chat.palettes.mochaText

val HomePageDescriptor = PageDescriptor {
    id = "menu:home"
    icon = Material.CAMPFIRE
    name = component {
        text("主页") with mochaText
    }
}
