package plutoproject.framework.common.util.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import plutoproject.framework.common.util.config.decoder.CharDecoder
import plutoproject.framework.common.util.config.decoder.ComponentDecoder

@OptIn(ExperimentalHoplite::class)
fun ConfigLoaderBuilder(): ConfigLoaderBuilder {
    return ConfigLoaderBuilder.empty()
        .withClassLoader(ConfigLoaderBuilder::class.java.classLoader)
        .withExplicitSealedTypes()
        .addDefaults()
        .addDecoder(ComponentDecoder)
        .addDecoder(CharDecoder)
}
