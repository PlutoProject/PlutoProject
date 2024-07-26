package ink.pmc.essentials

import ink.pmc.advkt.component.*
import ink.pmc.advkt.sound.key
import ink.pmc.advkt.sound.sound
import ink.pmc.advkt.title.*
import ink.pmc.utils.visual.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Ticks
import java.time.Duration
import java.util.UUID

val GM_SURVIVAL = Component.text("生存模式")

val GM_CREATIVE = Component.text("创造模式")

val GM_ADVENTURE = Component.text("冒险模式")

val GM_SPECTATOR = Component.text("旁观模式")

val COMMAND_GM_SUCCCEED = component {
    text("已将游戏模式切换为 ") with mochaPink
    text("<gamemode>") with mochaText
}

val COMMAND_GM_OTHER_SUCCCEED = component {
    text("已将 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("的游戏模式切换为 ") with mochaPink
    text("<gamemode>") with mochaText
}

val COMMAND_GM_FAILED = component {
    text("你已经处于该模式了") with mochaMaroon
}

val COMMAND_GM_FAILED_OTHER = component {
    text("该玩家已经处于该模式了") with mochaMaroon
}

val COMMAND_ALIGN_SUCCEED = component {
    text("已对齐你的视角和位置") with mochaPink
}

val COMMAND_ALIGN_POS_SUCCEED = component {
    text("已对齐你的位置") with mochaPink
}

val COMMAND_ALIGN_VIEW_SUCCEED = component {
    text("已对齐你的视角") with mochaPink
}

val COMMAND_HAT_SUCCEED = component {
    text("享受你的新帽子吧！") with mochaPink
}

val COMMAND_HAT_FAILED_EMPTY_HAND = component {
    text("你的手上似乎空空如也") with mochaMaroon
    newline()
    text("将你想要戴在头上的物品放入手中，然后再试一次吧") with mochaSubtext0
}

val COMMAND_HAT_SUCCEED_OTHER = component {
    text("已将你手中的物品戴在 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("的头上") with mochaPink
}

val COMMAND_HAT_FAILED_EXISTED_OTHER = component {
    text("该玩家的头上似乎已经有物品了") with mochaMaroon
}

val TELEPORT_PREPARING_TITLE = title {
    times {
        fadeIn(Ticks.duration(5))
        stay(Duration.ofMinutes(1))
        fadeOut(Duration.ZERO)
    }
    mainTitle {
        text("传送中") with mochaPink
    }
    subTitle {
        text("正在准备区块") with mochaText
    }
}

val TELEPORT_PREPARING_SOUND = sound {
    key(Key.key("block.decorated_pot.insert"))
}

val TELEPORT_SUCCEED_MAINTITLE_ARRAY = arrayOf(
    "落地！",
    "降落！",
    "到站了~",
    "请带好随身行李",
)

val TELEPORT_SUCCEED_MAINTITLE
    get() = TELEPORT_SUCCEED_MAINTITLE_ARRAY.random()

val TELEPORT_SUCCEED_TITLE
    get() = title {
        times {
            fadeIn(Ticks.duration(5))
            stay(Ticks.duration(35))
            fadeOut(Ticks.duration(20))
        }
        mainTitle {
            text(TELEPORT_SUCCEED_MAINTITLE) with mochaGreen
        }
        subTitle {
            text("已传送至目标位置") with mochaText
        }
    }

val TELEPORT_SUCCEED_SOUND = sound {
    key(Key.key("entity.ender_dragon.flap"))
}

val TELEPORT_FAILED_TITLE = title {
    times {
        fadeIn(Ticks.duration(5))
        stay(Ticks.duration(35))
        fadeOut(Ticks.duration(20))
    }
    mainTitle {
        text("传送失败") with mochaMaroon
    }
    subTitle {
        text("无法找到安全位置") with mochaText
    }
}

val TELEPORT_FAILED_SOUND = sound {
    key(Key.key("block.amethyst_cluster.break"))
}

val TELEPORT_EXPIRE = component {
    text("该传送请求将在") with mochaSubtext0
    text("<expire>") with mochaText
    text("后过期") with mochaSubtext0
}

val TELEPORT_REQUEST_AUTO_CANCEL = component {
    text("此前发送给 ") with mochaSubtext0
    text("<player> ") with mochaFlamingo
    text("的传送请求已自动取消") with mochaSubtext0
}

val TELEPORT_REQUEST_ACCEPTED_SOURCE = component {
    text("<player> ") with mochaFlamingo
    text("接受了你的传送请求") with mochaGreen
}

val TELEPORT_REQUEST_ACCEPTED = component {
    text("你接受了 ") with mochaGreen
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaGreen
}

val TELEPORT_REQUEST_DENYED_SOURCE = component {
    text("<player> ") with mochaFlamingo
    text("拒绝了你的传送请求") with mochaMaroon
}

val TELEPORT_REQUEST_DENYED = component {
    text("你拒绝了 ") with mochaMaroon
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaMaroon
}

val TELEPORT_REQUEST_IGNORED = component {
    text("你忽略了 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaYellow
}

val TELEPORT_REQUEST_EXPIRED = component {
    text("来自 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("的传送请求已过期") with mochaYellow
}

val TELEPORT_REQUEST_EXPIRED_SOURCE = component {
    text("你向 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("发送的传送请求已过期") with mochaYellow
}

val TELEPORT_REQUEST_CANCELED = component {
    text("来自 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("的传送请求已被取消") with mochaYellow
}

val TELEPORT_REQUEST_CANCELED_SOURCE = component {
    text("你取消了发送给 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaYellow
}

val TELEPORT_REQUEST_CANCELED_OFFLINE = component {
    text("玩家 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("已离线，传送请求自动取消") with mochaYellow
}

val COMMAND_TPA_AFK = component {
    text("对方目前正处于离开状态，可能无法及时查看请求") with mochaSubtext0
}

val COMMAND_TPA_SUCCEED = component {
    text("已请求传送到 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("那里") with mochaPink
}

val COMMAND_TPAHERE_SUCCEED = component {
    text("已请求 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("传送到你这里") with mochaPink
}

val TELEPORT_TPA_RECEIVED = component {
    text("<player> ") with mochaFlamingo
    text("想要传送到你这里") with mochaPink
}

val TELEPORT_TPAHERE_RECEIVED = component {
    text("<player> ") with mochaFlamingo
    text("想要你传送到他那里") with mochaPink
}

@Suppress("FunctionName")
fun TELEPORT_OPERATION(id: UUID) = component {
    text("[✔ 接受] ") with mochaGreen with showText { text("点击以接受") with mochaGreen } with runCommand("/essentials:tpaccept $id")
    text("[❌ 拒绝] ") with mochaMaroon with showText { text("点击以拒绝") with mochaMaroon } with runCommand("/essentials:tpdeny $id")
    text("[❓ 忽略]") with mochaYellow with showText { text("点击以忽略") with mochaYellow } with runCommand("/essentials:tpignore $id")
}