package plutoproject.feature.common.serverSelector

import ink.pmc.advkt.component.text
import ink.pmc.advkt.title.*
import net.kyori.adventure.util.Ticks
import plutoproject.framework.common.util.chat.palettes.mochaGreen
import plutoproject.framework.common.util.chat.palettes.mochaText

private val teleportSucceedMainTitles = arrayOf(
    "落地！",
    "到站了~",
    "请带好随身行李",
    "开门请当心",
    "下车请注意安全"
)

val teleportSucceedTitle
    get() = title {
        times {
            fadeIn(Ticks.duration(5))
            stay(Ticks.duration(35))
            fadeOut(Ticks.duration(20))
        }
        mainTitle {
            text(teleportSucceedMainTitles.random()) with mochaGreen
        }
        subTitle {
            text("已传送至目标位置") with mochaText
        }
    }
