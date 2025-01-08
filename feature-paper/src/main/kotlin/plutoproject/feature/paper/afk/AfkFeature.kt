package plutoproject.feature.paper.afk

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature

@Feature(
    id = "afk",
    platform = Platform.PAPER,
)
class AfkFeature : PaperFeature() {
}
