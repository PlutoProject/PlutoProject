package plutoproject.framework.common.util.data.uuid

import java.util.*

fun UUID.toShortUuidString(): String = toString().replace("-", "")

fun String.convertShortUuidToLong(): String {
    check(length == 32) { "Not a valid short uuid" }
    return replace(
        Regex("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{12})"),
        "$1-$2-$3-$4-$5"
    )
}
