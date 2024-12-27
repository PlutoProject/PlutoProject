package plutoproject.framework.common.api.feature

import plutoproject.framework.common.util.jvm.extractFileFromJar
import java.io.File

abstract class AbstractFeature<S : Any, P : Any> : Feature<S, P> {
    override lateinit var server: S
    override lateinit var plugin: P
    override lateinit var dataFolder: File

    override fun saveConfig(resourcePrefix: String): File =
        extractFileFromJar(
            "$resourcePrefix/config.conf",
            dataFolder.toPath().resolve("config.conf")
        )

    override fun saveResource(resourcePrefix: String, path: String): File =
        extractFileFromJar(
            "$resourcePrefix/$path",
            dataFolder.toPath().resolve(path)
        )
}
