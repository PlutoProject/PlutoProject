package plutoproject.framework.common.api.feature

import plutoproject.framework.common.util.featureResourcePrefix
import plutoproject.framework.common.util.jvm.extractFileFromJar
import java.io.File

abstract class AbstractFeature<S : Any, P : Any> : Feature<S, P> {
    override lateinit var server: S
    override lateinit var plugin: P
    override lateinit var dataFolder: File

    override fun saveConfig(): File =
        extractFileFromJar(
            "$featureResourcePrefix/config.conf",
            dataFolder.toPath().resolve("config.conf")
        )

    override fun saveResource(path: String): File =
        extractFileFromJar(
            "$featureResourcePrefix/$path",
            dataFolder.toPath().resolve(path)
        )
}
