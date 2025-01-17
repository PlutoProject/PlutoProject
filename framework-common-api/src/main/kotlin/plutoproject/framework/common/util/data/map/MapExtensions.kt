package plutoproject.framework.common.util.data.map

fun <K, V, R1, R2> Map<K, V>.mapKeysAndValues(action: (Map.Entry<K, V>) -> Pair<R1, R2>): Map<R1, R2> {
    return entries.associate { action(it) }
}
