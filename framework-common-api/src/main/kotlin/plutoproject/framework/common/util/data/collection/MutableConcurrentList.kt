package plutoproject.framework.common.util.data.collection

import java.util.*

fun <T> mutableConcurrentListOf(vararg elements: T): MutableList<T> {
    return Collections.synchronizedList(mutableListOf(*elements))
}
