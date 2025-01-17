package ink.pmc.essentials

import ink.pmc.advkt.component.*
import ink.pmc.advkt.component.replace
import ink.pmc.advkt.sound.key
import ink.pmc.advkt.sound.sound
import ink.pmc.advkt.title.*
import ink.pmc.essentials.api.home.Home
import ink.pmc.essentials.api.home.HomeManager
import ink.pmc.essentials.api.teleport.random.RandomTeleportManager
import ink.pmc.essentials.api.warp.Warp
import ink.pmc.essentials.api.warp.WarpManager
import ink.pmc.framework.chat.*
import ink.pmc.framework.world.aliasOrName
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Ticks
import org.bukkit.Location
import java.time.Duration
import java.util.*
import kotlin.time.toKotlinDuration

val RANDOM_TELEPORT_SUCCEED = component {
    text("已将你传送到 ") with mochaPink
    text("<location>") with mochaText
    newline()
    text("本次传送尝试 ") with mochaSubtext0
    text("<attempts> ") with mochaText
    text("次，耗时 ") with mochaSubtext0
    text("<lastLookupTime>") with mochaText
}

val RANDOM_TELEPORT_SUCCEED_COST = component {
    raw(RANDOM_TELEPORT_SUCCEED)
    text("，花费 ") with mochaSubtext0
    text("<amount><symbol>") with mochaText
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

val COMMAND_RTP_COOLDOWN = component {
    text("传送冷却中...") with mochaSubtext0
    newline()
    text("请 ") with mochaSubtext0
    text("<time> ") with mochaText
    text("后再试") with mochaSubtext0
}

val COMMAND_ESS_RTP
    get() = component {
        val manager = RandomTeleportManager

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
        text("你当前设置的家数量已经到达上限，请删除一些再试") with mochaMaroon
        newline()
        text("当前家上限数量为 ") with mochaSubtext0
        text("${HomeManager.maxHomes} ") with mochaText
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
        text("家的名字最多只能使用 ") with mochaMaroon
        text("${HomeManager.nameLengthLimit} ") with mochaText
        text("个字符") with mochaMaroon
        newline()
        text("请缩短一些后再试") with mochaSubtext0
    }

val COMMAND_SETHOME_SUCCEED = component {
    text("已设置名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的家") with mochaPink
}

val COMMAND_HOME_NOT_EXISTED = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的家不存在") with mochaMaroon
}

val COMMAND_EDITHOME_ALREADY_PREFERRED = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的家已经是首选了") with mochaMaroon
}

val COMMAND_EDITHOME_PREFER_SUCCEED = component {
    text("已将名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的家设为首选") with mochaPink
}

val COMMAND_EDITHOME_ALREADY_STARRED = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的家已经收藏过了") with mochaMaroon
}

val COMMAND_EDITHOME_STAR_SUCCEED = component {
    text("已将名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的家收藏") with mochaPink
}

val COMMAND_EDITHOME_RENAME_SUCCEED = component {
    text("已将该家更名为 ") with mochaPink
    text("<new_name>") with mochaText
}

val COMMAND_EDITHOME_MOVE_SUCCEED = component {
    text("已将家 ") with mochaPink
    text("<name> ") with mochaText
    text("迁移到你所在的位置") with mochaPink
}

val COMMAND_HOME_NOT_EXISTED_UUID = component {
    text("无法通过指定的 ID 找到对应的家") with mochaMaroon
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

val COMMAND_WARP_NOT_EXISTED = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的地标不存在") with mochaMaroon
}

val COMMAND_PREFERRED_SPAWN_WARP_IS_NOT_SPAWN = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的地标不是一个出生点") with mochaMaroon
}

val COMMAND_PREFERRED_SPAWN_SUCCEED = component {
    text("已将你的默认出生点设为 ") with mochaPink
    text("<name>") with mochaText
}

val COMMAND_PREFERRED_SPAWN_FAILED_ALREADY = component {
    text("你的默认出生点已是 ") with mochaMaroon
    text("<name>") with mochaText
}

val COMMAND_WARP_FAILED_NOT_EXISTED_UUID = component {
    text("无法通过指定的 ID 找到对应的地标") with mochaMaroon
}

val COMMAND_WARP_SUCCEED = component {
    text("已传送到名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的地标") with mochaPink
}

val COMMAND_WARP_SUCCEED_ALIAS = component {
    text("已传送到名为 ") with mochaPink
    text("<alias> ") with mochaText
    text("(<name>) ") with mochaSubtext0
    text("的地标") with mochaPink
}

val COMMAND_SETWARP_FAILED_EXISTED = component {
    text("名为 ") with mochaMaroon
    text("<name> ") with mochaText
    text("的地标已存在") with mochaMaroon
}

val COMMAND_SETWARP_FAILED_NOT_VALID = component {
    text("地标的名字只可以包含字母、数字、下划线") with mochaMaroon
    newline()
    text("不可以使用中文、空格等字符") with mochaSubtext0
}

val COMMAND_SETWARP_FAILED_LENGTN_LIMIT
    get() = component {
        text("地标的名字最多只能使用 ") with mochaMaroon
        text("${WarpManager.nameLengthLimit} ") with mochaText
        text("个字符") with mochaMaroon
        newline()
        text("请缩短一些后再试") with mochaSubtext0
    }

val COMMAND_SETWARP_SUCCEED = component {
    text("已设置名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的地标") with mochaPink
}

val COMMAND_SETWARP_SUCCEED_ALIAS = component {
    text("已设置名为 ") with mochaPink
    text("<alias> ") with mochaText
    text("(<name>) ") with mochaSubtext0
    text("的地标") with mochaPink
}

val COMMAND_DELWARP_SUCCEED = component {
    text("已删除名为 ") with mochaPink
    text("<name> ") with mochaText
    text("的地标") with mochaPink
}

val COMMAND_DELWARP_SUCCEED_ALIAS = component {
    text("已删除名为 ") with mochaPink
    text("<alias> ") with mochaText
    text("(<name>) ") with mochaSubtext0
    text("的地标") with mochaPink
}

val COMMAND_BACK_FAILED_NO_LOC = component {
    text("还没有记录到你先前的位置") with mochaMaroon
    newline()
    text("进行一段时间游玩后位置才会被记录") with mochaSubtext0
}

val COMMAND_BACK_SUCCEED = component {
    text("已回到你先前的位置") with mochaPink
}

const val DEFAULT_ECONOMY_SYMBOL = "\uD83C\uDF1F"

val RANDOM_TELEPORT_BALANCE_NOT_ENOUGH = component {
    text("货币不足，进行随机传送需要 ") with mochaMaroon
    text("<amount> <symbol>") with mochaText
    newline()
    text("你当前拥有 ") with mochaSubtext0
    text("<balance> <symbol>") with mochaText
}

val UI_VIEWER_LOADING_TITLE = component {
    text("正在加载数据")
}

val UI_VIEWER_LOADING = component {
    text("正在加载数据...") with mochaSubtext0 without italic()
}

val UI_VIEWER_EMPTY = component {
    text("这里空空如也") with mochaText without italic()
}

private val UI_HOME_EMPTY_PROMPT = component {
    text("点击下方按钮或 ") with mochaSubtext0 without italic()
    text("/sethome ") with mochaLavender without italic()
    text("以留下你的足迹") with mochaSubtext0 without italic()
}

val UI_HOME_EMPTY_LORE = listOf(
    UI_HOME_EMPTY_PROMPT
)

val UI_HOME_EMPTY_LORE_OTHER = listOf(
    component { text("该玩家未设置家") with mochaSubtext0 without italic() }
)

val UI_HOME_TITLE = component {
    text("<player> 的家")
}

val UI_HOME_TITLE_SELF = component {
    text("家")
}

val UI_HOME_ITEM_NAME = component {
    text("<name>") with mochaYellow without italic()
}

private val UI_HOME_PREFERRED = component {
    text("√ 首选的家") with mochaGreen without italic()
}

private val UI_HOME_STARRED = component {
    text("✨ 收藏的家") with mochaYellow without italic()
}

private val UI_HOME_ITEM_LORE_LOC = component {
    text("<world> <x>, <y>, <z>") with mochaSubtext0 without italic()
}

@Suppress("FunctionName")
fun UI_HOME_ITEM_LORE(home: Home): List<Component> {
    val loc = home.location
    return mutableListOf<Component>().apply {
        add(component {
            raw(
                UI_HOME_ITEM_LORE_LOC
                    .replace("<world>", loc.world.aliasOrName)
                    .replace("<x>", "${loc.blockX}")
                    .replace("<y>", "${loc.blockY}")
                    .replace("<z>", "${loc.blockZ}")
            )
        })
        if (home.isPreferred) add(UI_HOME_PREFERRED)
        if (home.isStarred) add(UI_HOME_STARRED)
        add(Component.empty())
        add(component {
            text("左键 ") with mochaLavender without italic()
            text("传送到该位置") with mochaText without italic()
        })
        add(component {
            text("右键 ") with mochaLavender without italic()
            text("编辑家") with mochaText without italic()
        })
    }
}

val VIEWER_PAGING = component {
    text("页 <curr>/<total>") with mochaText without italic()
}

val VIEWING_PAGE_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("下一页") with mochaText without italic()
    },
    component {
        text("右键 ") with mochaLavender without italic()
        text("上一页") with mochaText without italic()
    }
)

val VIEWER_PAGING_SOUND = sound {
    key(Key.key("item.book.page_turn"))
}

val UI_WARP_TITLE = component {
    text("地标")
}

val UI_WARP_ITEM_NAME = component {
    text("<name>") with mochaPink without italic()
}

val UI_WARP_ITEM_NAME_ALIAS = component {
    text("<alias> ") with mochaPink without italic()
    text("(<name>)") with mochaSubtext0 without italic()
}

private val UI_WARP_ITEM_LORE_LOC = component {
    text("<world> <x>, <y>, <z>") with mochaSubtext0 without italic()
}

@Suppress("FunctionName")
fun UI_WARP_ITEM_LORE(warp: Warp): List<Component> {
    val loc = warp.location
    return listOf(
        component {
            raw(
                UI_WARP_ITEM_LORE_LOC
                    .replace("<world>", loc.world.aliasOrName)
                    .replace("<x>", "${loc.blockX}")
                    .replace("<y>", "${loc.blockY}")
                    .replace("<z>", "${loc.blockZ}")
            )
        },
        Component.empty(),
        component {
            text("左键 ") with mochaLavender without italic()
            text("传送到该位置") with mochaText without italic()
        },
    )
}

val UI_WARP_EMPTY_LORE = listOf(
    component { text("服务器未设置地标") with mochaSubtext0 without italic() }
)

val UI_HOME_EDITOR_TITLE = component {
    text("编辑 <name>")
}

val UI_HOME_RENAME = component {
    text("重命名") with mochaText without italic()
}

val UI_HOME_RENAME_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("重命名该家") with mochaText without italic()
    }
)

val UI_HOME_CHANGE_LOCATION = component {
    text("迁移") with mochaText without italic()
}

val UI_HOME_CHANGE_LOCATION_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("将该家迁移到你所在的位置") with mochaText without italic()
    }
)

val UI_HOME_DELETE = component {
    text("删除") with mochaText without italic()
}

val UI_HOME_DELETE_LORE = listOf(
    component { text("该操作不可撤销") with mochaRed without italic() },
    Component.empty(),
    component {
        text("Shift + 左键 ") with mochaLavender without italic()
        text("删除该家") with mochaText without italic()
    }
)

val UI_HOME_EDIT_SUCCEED = component {
    text("√ 已保存") with mochaGreen without italic()
}

val UI_HOME_PREFER = component {
    text("设为首选") with mochaText without italic()
}

val UI_HOME_PREFER_LORE = listOf(
    component { text("设为首选后，") with mochaSubtext0 without italic() },
    component {
        text("使用 ") with mochaSubtext0 without italic()
        text("/home ") with mochaLavender without italic()
        text("或「手账」将默认传送该家") with mochaSubtext0 without italic()
    },
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("将该家设为首选") with mochaText without italic()
    }
)

val UI_HOME_PREFER_UNSET = component {
    text("取消首选") with mochaText without italic()
}


val UI_HOME_PREFER_UNSET_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("将该家取消首选") with mochaText without italic()
    }
)

val UI_HOME_STAR = component {
    text("收藏") with mochaText without italic()
}

val UI_HOME_STAR_LORE = listOf(
    component { text("收藏后，该家将靠前显示") with mochaSubtext0 without italic() },
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("将该家收藏") with mochaText without italic()
    }
)

val UI_HOME_STAR_UNSET = component {
    text("取消收藏") with mochaText without italic()
}

val UI_HOME_STAR_UNSET_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("将该家取消收藏") with mochaText without italic()
    }
)

val UI_HOME_EDIT_SUCCEED_SOUND = sound {
    key(Key.key("block.note_block.bell"))
}

val UI_HOME_EDITOR_REMOVE_SOUND = sound {
    key(Key.key("block.decorated_pot.break"))
}

val UI_HOME_EDITOR_RENAME_TITLE = component {
    text("重命名 <name>")
}

val UI_HOME_EDITOR_RENAME_EXIT_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("退出编辑") with mochaText without italic()
    }
)

@Suppress("FunctionName")
fun UI_HOME_EDITOR_RENAME_SAVE_EDITING(home: Home): List<Component> {
    val loc = home.location
    return listOf(
        component {
            raw(
                UI_HOME_ITEM_LORE_LOC
                    .replace("<world>", loc.world.aliasOrName)
                    .replace("<x>", "${loc.blockX}")
                    .replace("<y>", "${loc.blockY}")
                    .replace("<z>", "${loc.blockZ}")
            )
        },
        Component.empty(),
        component {
            text("左键 ") with mochaLavender without italic()
            text("保存并退出") with mochaText without italic()
        }
    )
}

val UI_HOME_EDITOR_RENAME_SAVE_INVALID_LORE = listOf(
    Component.empty(),
    component { text("仅可使用字母、数字、下划线") with mochaMaroon without italic() }
)

val UI_HOME_EDITOR_RENAME_SAVE_TOO_LONG
    get() = listOf(
        Component.empty(),
        component {
            text("名称过长，最多使用 ") with mochaMaroon without italic()
            text("${HomeManager.nameLengthLimit} ") with mochaText without italic()
            text("个字符") with mochaMaroon without italic()
        }
    )

val UI_HOME_EDITOR_RENAME_SAVE_EXISTED = listOf(
    Component.empty(),
    component {
        component { text("已存在同名的家") with mochaMaroon without italic() }
    }
)

val UI_HOME_EDITOR_RENAME_SAVED = listOf(
    Component.empty(),
    UI_HOME_EDIT_SUCCEED
)

val UI_HOME_EDITOR_RENAME_INVALID_SOUND = sound {
    key(Key.key("block.note_block.didgeridoo"))
}

val UI_HOME_CREATOR_TITLE = component {
    text("创建家")
}

val UI_HOME_CREATOR_LEFT_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("返回上一页") with mochaText without italic()
    }
)

const val UI_HOME_CREATOR_INPUT = "输入名称..."

@Suppress("FunctionName")
fun UI_HOME_CREATOR_OUTPUT_LORE(loc: Location): List<Component> {
    return listOf(
        component {
            raw(
                UI_HOME_ITEM_LORE_LOC
                    .replace("<world>", loc.world.aliasOrName)
                    .replace("<x>", "${loc.blockX}")
                    .replace("<y>", "${loc.blockY}")
                    .replace("<z>", "${loc.blockZ}")
            )
        },
        Component.empty(),
        component {
            text("左键 ") with mochaLavender without italic()
            text("创建家") with mochaText without italic()
        }
    )
}

val UI_HOME_VIEWER_CREATE = component {
    text("创建家") with mochaText without italic()
}

val UI_HOME_VIEWER_CREATE_LORE = listOf(
    Component.empty(),
    component {
        text("左键 ") with mochaLavender without italic()
        text("在当前位置创建家") with mochaText without italic()
    }
)

val COMMAND_SPAWN_FAILED_NOT_SET = component {
    text("服务器未设置出生点位置") with mochaMaroon
}
