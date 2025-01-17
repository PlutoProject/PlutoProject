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

const val DEFAULT_ECONOMY_SYMBOL = "\uD83C\uDF1F"

val UI_VIEWER_LOADING_TITLE = component {
    text("正在加载数据")
}

val UI_VIEWER_LOADING = component {
    text("正在加载数据...") with mochaSubtext0 without italic()
}

val UI_VIEWER_EMPTY = component {
    text("这里空空如也") with mochaText without italic()
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

val COMMAND_SPAWN_FAILED_NOT_SET = component {
    text("服务器未设置出生点位置") with mochaMaroon
}
