package ink.pmc.essentials

import ink.pmc.advkt.component.*
import ink.pmc.advkt.sound.key
import ink.pmc.advkt.sound.sound
import ink.pmc.advkt.title.*
import ink.pmc.essentials.api.Essentials
import ink.pmc.utils.chat.DURATION
import ink.pmc.utils.visual.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Ticks
import java.time.Duration
import java.util.*
import kotlin.time.toKotlinDuration

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
    key(Key.key("block.amethyst_cluster.hit"))
}

val TELEPORT_SUCCEED_MAINTITLE_ARRAY = arrayOf(
    "落地！",
    "到站了~",
    "请带好随身行李",
    "开门请当心",
    "下车请注意安全"
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

val TELEPORT_SUCCEED_TITLE_SAFE
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
            text("已传送至附近的安全位置") with mochaText
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

val TELEPORT_FAILED_TIMEOUT_TITLE = title {
    times {
        fadeIn(Ticks.duration(5))
        stay(Ticks.duration(35))
        fadeOut(Ticks.duration(20))
    }
    mainTitle {
        text("传送失败") with mochaMaroon
    }
    subTitle {
        text("等待已超时") with mochaText
    }
}

val TELEPORT_FAILED_SOUND = sound {
    key(Key.key("block.amethyst_cluster.break"))
}

val TELEPORT_REQUEST_DENIED_SOUND = sound {
    key(Key.key("entity.villager.no"))
}

val TELEPORT_REQUEST_CANCELLED_SOUND = sound {
    key(Key.key("block.chain.hit"))
}

val TELEPORT_EXPIRE = component {
    text("该请求将在 ") with mochaSubtext0
    text("<expire> ") with mochaText
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

val TELEPORT_REQUEST_DENIED_SOURCE = component {
    text("<player> ") with mochaFlamingo
    text("拒绝了你的传送请求") with mochaMaroon
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

val TELEPORT_REQUEST_CANCELED_OFFLINE = component {
    text("玩家 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("已离线，传送请求自动取消") with mochaYellow
}

val COMMAND_TPA_AFK = component {
    text("对方目前正处于离开状态，可能无法及时查看请求") with mochaSubtext0
}

val TELEPORT_REQUEST_RECEIVED_SOUND = sound {
    key(Key.key("block.decorated_pot.insert"))
}

val COMMAND_TPA_SUCCEED = component {
    text("已向 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("请求传送至其所在位置") with mochaPink
}

val COMMAND_TPA_FAILED_SELF = component {
    text("你不能向自己发送传送请求") with mochaMaroon
}

val COMMAND_TPA_FAILED_TARGET_BUSY = component {
    text("对方仍有未处理的传送请求，请稍后再试") with mochaMaroon
}

val COMMAND_TPA_FAILED_NOT_ALLOWED_GO = component {
    text("玩家 ") with mochaMaroon
    text("<player> ") with mochaFlamingo
    text("所在的世界不允许被传送") with mochaMaroon
}

val COMMAND_TPA_FAILED_NOT_ALLOWED_COME = component {
    text("你所在的世界不允许被传送") with mochaMaroon
}

val COMMAND_TPAHERE_SUCCEED = component {
    text("已向 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("请求传送到你所在的位置") with mochaPink
}

val TELEPORT_TPA_RECEIVED = component {
    text("<player> ") with mochaFlamingo
    text("请求传送到") with mochaPink
    text("你所在的位置") with mochaPink with underlined()
}

val TELEPORT_TPAHERE_RECEIVED = component {
    text("<player> ") with mochaFlamingo
    text("请求将你传送至") with mochaPink
    text("其所在的位置") with mochaPink with underlined()
}

val COMMAND_TPACCEPT_SUCCEED = component {
    text("已接受来自 ") with mochaGreen
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaGreen
}

val COMMAND_TPDENY_SUCCEED = component {
    text("已拒绝来自 ") with mochaMaroon
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaMaroon
}

val COMMAND_TPACCEPT_FAILED_NO_PENDING = component {
    text("你暂时没有未接受的传送请求") with mochaMaroon
}

val COMMAND_TPACCEPT_FAILED_NO_REQUEST = component {
    text("你没有来自 ") with mochaMaroon
    text("<player> ") with mochaFlamingo
    text("的未接受请求") with mochaMaroon
}

val COMMAND_TPACCEPT_FAILED_NO_REQUEST_ID = component {
    text("请求已过期或不存在") with mochaMaroon
    newline()
    text("如果你认为这是一个错误，请上报给管理组") with mochaSubtext0
}

val COMMAND_TPCANCEL_SUCCEED = component {
    text("已取消发送给 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaYellow
}

val COMMAND_TPCANCEL_SUCCEED_OTHER = component {
    text("已取消 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("发送给 ") with mochaYellow
    text("<dest> ") with mochaFlamingo
    text("的传送请求") with mochaYellow
}

val COMMAND_TPCANCEL_OTHER_NOTIFY = component {
    text("管理员取消了你发送给 ") with mochaYellow
    text("<player> ") with mochaFlamingo
    text("的传送请求") with mochaYellow
}

val COMMAND_TPCANCEL_NO_REQUEST = component {
    text("你没有未完成的传送请求") with mochaMaroon
}

val COMMAND_TPCANCEL_NO_REQUEST_OTHER = component {
    text("<player> ") with mochaFlamingo
    text("没有未完成的传送请求") with mochaMaroon
}

@Suppress("FunctionName")
fun TELEPORT_OPERATION(id: UUID) = component {
    text("[✔ 接受] ") with mochaGreen with showText { text("点击以接受") with mochaGreen } with runCommand("/essentials:tpaccept $id")
    text("[❌ 拒绝] ") with mochaMaroon with showText { text("点击以拒绝") with mochaMaroon } with runCommand("/essentials:tpdeny $id")
}

val COMMAND_ETP_SUCCEED = component {
    text("已传送至 ") with mochaPink
    text("<location> ") with mochaText
}

val COMMAND_ETP_SUCCEED_OTHER = component {
    text("已将玩家 ") with mochaPink
    text("<player> ") with mochaFlamingo
    text("传送至 ") with mochaPink
    text("<location>") with mochaText
}

val RANDOM_TELEPORT_SUCCED = component {
    text("已将你传送到 ") with mochaPink
    text("<location>") with mochaText
    newline()
    text("本次传送尝试 ") with mochaSubtext0
    text("<attempts> ") with mochaText
    text("次，耗时 ") with mochaSubtext0
    text("<time>") with mochaText
}

val RANDOM_TELEPORT_SEARCHING_TITLE = title {
    times {
        fadeIn(Ticks.duration(5))
        stay(Duration.ofMinutes(1))
        fadeOut(Duration.ZERO)
    }
    mainTitle {
        text("传送中") with mochaPink
    }
    subTitle {
        text("正在搜寻目的地") with mochaText
    }
}

val RANDOM_TELEPORT_SEARCHING_SOUND = sound {
    key(Key.key("entity.tnt.primed"))
}

val RANDOM_TELEPORT_SEARCHING_FAILED = component {
    text("这次运气似乎不太好...") with mochaYellow
    newline()
    text("如果此问题总是发生，请报告给管理组") with mochaSubtext0
}

val RANDOM_TELEPORT_FAILED_IN_PROGRESS = component {
    text("你已有一个正在处理的随机传送操作，请不要着急") with mochaYellow
}

val COMMAND_RTP_NOT_ENABLED = component {
    text("该世界未启用随机传送") with mochaMaroon
}

val COMMAND_ESS_RTP
    get() = component {
        val manager = Essentials.randomTeleportManager

        text("随机传送状态：") with mochaFlamingo
        newline()

        text("  - ") with mochaSubtext0
        text("已处理刻数：") with mochaText
        text("${manager.tickCount}") with mochaLavender
        newline()

        text("  - ") with mochaSubtext0
        text("上次刻处理用时：") with mochaText
        raw(DURATION(Duration.ofMillis(manager.lastTickTime).toKotlinDuration())) with mochaLavender

        newline()
        empty()
        newline()

        text("世界缓存数：") with mochaFlamingo
        newline()

        val worlds = manager.enabledWorlds
        worlds.forEach {
            val caches = manager.getCaches(it).size
            text("  - ") with mochaSubtext0
            text("${it.name}：") with mochaText
            text(caches) with mochaLavender
            if (worlds.last() != it) {
                newline()
            }
        }
    }

val COMMAND_ESS_RTP_PERF_START = component {
    text("已开启性能测试，再次使用本指令以关闭") with mochaPink
}

val COMMAND_ESS_RTP_PERF_END = component {
    text("已关闭性能测试") with mochaPink
}

val COMMAND_SETHOME_FAILED_REACH_LIMIT
    get() = component {
        val manager = Essentials.homeManager
        text("你当前设置的家数量已经到达上限，请删除一些再试") with mochaMaroon
        newline()
        text("当前家上限数量为 ") with mochaSubtext0
        text("${manager.maxHomes} ") with mochaText
        text("个") with mochaSubtext0
    }

val COMMAND_SETHOME_FAILED_EXISTED = component {
    text("你已经有一个名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的家了") with mochaMaroon
    newline()
    text("请删除或更换一个名字后再试") with mochaSubtext0
}

val COMMAND_SETHOME_FAILED_NOT_VALID = component {
    text("家的名字只可以包含字母、数字、下划线") with mochaMaroon
    newline()
    text("不可以使用中文、空格等字符") with mochaSubtext0
}

val COMMAND_SETHOME_FAILED_LENGTN_LIMIT
    get() = component {
        val manager = Essentials.homeManager
        text("家的名字最多只能使用 ") with mochaMaroon
        text("${manager.nameLengthLimit} ") with mochaText
        text("个字符") with mochaMaroon
        newline()
        text("请缩短一些后再试") with mochaSubtext0
    }

val COMMAND_SETHOME_SUCCEED = component {
    text("已成功设置名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的家") with mochaPink
}

val COMMAND_SETHOME_PREFERRED = component {
    text("已将你首选的家设置为 ") with mochaPink
    text("<name> ") with mochaText
}

val COMMAND_HOME_NOT_EXISTED = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的家不存在") with mochaMaroon
}

val COMMAND_HOME_NOT_EXISTED_UUID = component {
    text("无法通过指定的 ID 找到对应的家") with mochaMaroon
}

val COMMAND_HOME_FAILED_NO_PREFREED = component {
    text("你还没有首选的家，请先设置一个家再试") with mochaMaroon
}

val COMMAND_HOME_SUCCEED = component {
    text("已传送到名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的家") with mochaPink
}

val COMMAND_DELHOME_SUCCEED = component {
    text("已删除名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的家") with mochaPink
}