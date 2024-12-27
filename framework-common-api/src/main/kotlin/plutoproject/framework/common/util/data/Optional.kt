package plutoproject.framework.common.util.data

import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> Optional(obj: T?): Optional<T> {
    return Optional.ofNullable(obj) as Optional<T>
}

val EmptyOptional: Optional<Nothing> = Optional.empty()
