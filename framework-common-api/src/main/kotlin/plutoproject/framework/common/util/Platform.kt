package plutoproject.framework.common.util

import java.io.File

lateinit var serverThread: Thread
lateinit var pluginDataFolder: File
lateinit var featureDataFolder: File
lateinit var frameworkDataFolder: File

fun getFrameworkModuleDataFolder(id: String): File {
    val file = frameworkDataFolder.resolve(id)
    file.parentFile.mkdirs()
    return file
}

fun File.initPluginDataFolder() {
    pluginDataFolder = this
    pluginDataFolder.mkdirs()
    featureDataFolder = pluginDataFolder.resolve("feature${File.separator}")
    frameworkDataFolder = pluginDataFolder.resolve("framework${File.separator}")
    featureDataFolder.mkdirs()
    featureDataFolder.mkdirs()
}
