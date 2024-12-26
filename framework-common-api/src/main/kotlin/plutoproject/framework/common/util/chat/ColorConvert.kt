package plutoproject.framework.common.util.chat

import net.kyori.adventure.text.format.TextColor
import java.awt.Color

fun Int.toRgbaColor(): Color = Color(
    (this shr 16) and 0xFF,
    (this shr 8) and 0xFF,
    this and 0xFF,
    (this shr 24) and 0xFF
)

@Suppress("NOTHING_TO_INLINE")
inline fun Color.toTextColor(): TextColor {
    return TextColor.color(red, green, blue)
}
