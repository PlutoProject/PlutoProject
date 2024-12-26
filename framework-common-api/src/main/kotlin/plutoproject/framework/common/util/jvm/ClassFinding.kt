package plutoproject.framework.common.util.jvm

fun findClass(fqn: String): Class<*>? {
    return runCatching {
        Class.forName(fqn)
    }.getOrNull()
}