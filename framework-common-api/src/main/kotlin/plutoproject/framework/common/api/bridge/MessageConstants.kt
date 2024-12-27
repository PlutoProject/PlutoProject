package plutoproject.framework.common.api.bridge

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.text
import plutoproject.framework.common.util.chat.palettes.MOCHA_MAROON
import plutoproject.framework.common.util.chat.palettes.MOCHA_TEXT

object MessageConstants {
    val playerNotFound = component {
        text("玩家 ") with MOCHA_MAROON
        text("<player> ") with MOCHA_TEXT
        text("未找到") with MOCHA_MAROON
    }

    val serverNotFound = component {
        text("服务器 ") with MOCHA_MAROON
        text("<server> ") with MOCHA_TEXT
        text("未找到") with MOCHA_MAROON
    }
}