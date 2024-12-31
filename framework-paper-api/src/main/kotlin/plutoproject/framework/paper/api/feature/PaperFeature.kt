package plutoproject.framework.paper.api.feature

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.metadata.AbstractFeature

abstract class PaperFeature : AbstractFeature() {
    final override val platform: Platform = Platform.PAPER
    final override val resourcePrefixInJar: String
        get() = "feature/paper/$id"
}
