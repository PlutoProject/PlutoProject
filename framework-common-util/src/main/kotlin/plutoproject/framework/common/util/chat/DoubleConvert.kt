package plutoproject.framework.common.util.chat

import java.text.DecimalFormat

fun Double.toCurrencyFormattedString(): String {
    val decimalFormat = DecimalFormat("#,##0.00")
    return decimalFormat.format(this)
}
