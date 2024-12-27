package plutoproject.framework.common.api.feature

import java.io.File

interface Feature<S : Any, P : Any> {
    val server: S
    val plugin: P
    val dataFolder: File

    fun onLoad() {}

    fun onEnable() {}

    fun onReload() {}

    fun onDisable() {}

    fun saveConfig(resourcePrefix: String): File

    fun saveResource(resourcePrefix: String, path: String): File
}
