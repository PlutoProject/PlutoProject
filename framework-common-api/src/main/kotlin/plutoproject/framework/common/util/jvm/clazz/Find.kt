package plutoproject.framework.common.util.jvm.clazz

fun findClass(fqn: String): Class<*>? {
    return runCatching {
        Class.forName(fqn)
    }.getOrNull()
}