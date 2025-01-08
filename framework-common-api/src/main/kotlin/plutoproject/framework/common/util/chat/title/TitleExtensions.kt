package plutoproject.framework.common.util.chat.title

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import plutoproject.framework.common.util.chat.component.replace

fun Title.mainTitleReplace(pattern: String, str: String): Title =
    Title.title(title().replace(pattern, str), subtitle(), times())

fun Title.mainTitleReplace(pattern: String, component: Component): Title =
    Title.title(title().replace(pattern, component), subtitle(), times())

fun Title.mainTitleReplace(pattern: String, any: Any?): Title =
    Title.title(title().replace(pattern, any), subtitle(), times())

fun Title.subTitleReplace(pattern: String, str: String): Title =
    Title.title(title(), subtitle().replace(pattern, str), times())

fun Title.subTitleReplace(pattern: String, component: Component): Title =
    Title.title(title(), subtitle().replace(pattern, component), times())

fun Title.subTitleReplace(pattern: String, any: Any?): Title =
    Title.title(title(), subtitle().replace(pattern, any), times())
