package plutoproject.feature.paper.afk

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.text
import plutoproject.framework.common.util.chat.palettes.mochaSubtext0

val PLAYER_AFK_ENTER_ANNOUNCEMENT = component {
    text("* <player> 暂时离开了") with mochaSubtext0
}

val PLAYER_AFK_EXIT_ANNOUNCEMENT = component {
    text("* <player> 回来了") with mochaSubtext0
}
