package plutoproject.framework.common.util.data.uuid

import java.util.*

fun String.convertToUuid(): UUID = UUID.fromString(this)

fun String.convertToUuidOrNull(): UUID? =
    try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        null
    }
