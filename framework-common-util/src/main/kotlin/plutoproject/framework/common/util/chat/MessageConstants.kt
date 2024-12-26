package plutoproject.framework.common.util.chat

import ink.pmc.advkt.component.*
import plutoproject.framework.common.util.chat.palette.mochaMaroon
import plutoproject.framework.common.util.chat.palette.mochaSubtext0
import plutoproject.framework.common.util.chat.palette.mochaYellow

const val ECONOMY_SYMBOL = "\uD83C\uDF1F"

val NON_PLAYER = component {
    text("该命令仅限玩家使用") with mochaMaroon
}

val EMPTY_LINE = component { }

val PLUTO_PROJECT = component {
    miniMessage("<gradient:#c6a0f6:#f5bde6:#f0c6c6:#f4dbd6>星社 ᴘʀᴏᴊᴇᴄᴛ</gradient>")
}

val PLAYER_NOT_ONLINE = component {
    text("该玩家不在线") with mochaMaroon
}

val NO_PERMISSION = component {
    text("你似乎没有权限这么做") with mochaMaroon
    newline()
    text("如果你认为这是一个错误的话，请向管理组报告") with mochaSubtext0
}

val UNUSUAL_ISSUE = component {
    text("看起来你似乎遇见了一个很罕见的问题") with mochaMaroon
    newline()
    text("我们建议你反馈这个问题，有助于将服务器变得更好") with mochaSubtext0
}

val IN_PROGRESS = component {
    text("正在施工中...") with mochaMaroon
    newline()
    text("前面的路，以后再来探索吧！") with mochaSubtext0
}

val UI_CLOSE = component {
    text("关闭") with mochaMaroon without italic()
}

val UI_BACK = component {
    text("返回") with mochaYellow without italic()
}
