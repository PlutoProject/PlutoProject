package plutoproject.framework.common.util

import java.io.File

lateinit var serverThread: Thread
lateinit var pluginDataFolder: File
lateinit var featureDataFolder: File
lateinit var frameworkDataFolder: File
lateinit var featureResourcePrefix: String
lateinit var frameworkResourcePrefix: String

fun File.initPluginDataFolder() {
    pluginDataFolder = this
    if (!(pluginDataFolder.exists())) {
        pluginDataFolder.mkdirs()
    }
    featureDataFolder = File(pluginDataFolder, "feature${File.separator}")
    frameworkDataFolder = File(pluginDataFolder, "framework${File.separator}")
    if (!(featureDataFolder.exists())) {
        featureDataFolder.mkdirs()
    }
    if (!(frameworkDataFolder.exists())) {
        featureDataFolder.mkdirs()
    }
}
