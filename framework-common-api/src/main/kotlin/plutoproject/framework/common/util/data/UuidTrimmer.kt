package plutoproject.framework.common.util.data

import java.util.*

fun UUID.toTrimmedString(): String = toString().replace("-", "")
