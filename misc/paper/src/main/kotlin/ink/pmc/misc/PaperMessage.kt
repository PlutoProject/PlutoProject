package ink.pmc.misc

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.keybind
import ink.pmc.advkt.component.text
import ink.pmc.advkt.sound.*
import ink.pmc.advkt.title.*
import ink.pmc.framework.chat.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.title.Title
import kotlin.time.Duration.Companion.seconds

val SUICIDE = component {
    text("你结束了自己的生命...") with mochaFlamingo
}

fun elevatorGoUpTitle(curr: Int, total: Int): Title {
    return title {
        mainTitle {
            text(" ")
        }
        subTitle {
            text("电梯上行 ") with mochaYellow
            text("($curr/$total)") with mochaSubtext0
        }
        times {
            fadeIn(0.seconds)
            stay(1.seconds)
            fadeOut(0.seconds)
        }
    }
}

fun elevatorGoDownTitle(curr: Int, total: Int): Title {
    return title {
        mainTitle {
            text(" ")
        }
        subTitle {
            text("电梯下行 ") with mochaYellow
            text("($curr/$total)") with mochaSubtext0
        }
        times {
            fadeIn(0.seconds)
            stay(1.seconds)
            fadeOut(0.seconds)
        }
    }
}

val ELEVATOR_WORK_SOUND = sound {
    key(Key.key("entity.iron_golem.attack"))
    source(Sound.Source.BLOCK)
    volume(1f)
    pitch(1f)
}