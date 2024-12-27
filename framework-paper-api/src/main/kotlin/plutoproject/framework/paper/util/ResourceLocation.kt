package plutoproject.framework.paper.util

import net.minecraft.resources.ResourceLocation

fun ResourceLocation(namespace: String, path: String): ResourceLocation =
    ResourceLocation.fromNamespaceAndPath(namespace, path)

fun ResourceLocation(namespacedPath: String): ResourceLocation {
    val namespace = namespacedPath.substringBefore(':')
    val key = namespacedPath.substringAfter(':')
    return ResourceLocation(namespace, key)
}
