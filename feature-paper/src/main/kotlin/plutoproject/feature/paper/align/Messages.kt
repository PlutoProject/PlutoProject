package plutoproject.feature.paper.align

import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.text
import plutoproject.framework.common.util.chat.palettes.mochaPink

val COMMAND_ALIGN_SUCCEED = component {
    text("已对齐你的视角和位置") with mochaPink
}

val COMMAND_ALIGN_POS_SUCCEED = component {
    text("已对齐你的位置") with mochaPink
}

val COMMAND_ALIGN_VIEW_SUCCEED = component {
    text("已对齐你的视角") with mochaPink
}
