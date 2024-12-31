package plutoproject.framework.velocity.api.feature

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.metadata.AbstractFeature

abstract class VelocityFeature : AbstractFeature() {
    final override val platform: Platform = Platform.VELOCITY
    final override val resourcePrefixInJar: String
        get() = "feature/velocity/$id"
}
