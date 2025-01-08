package plutoproject.feature.paper.warp

import plutoproject.framework.common.api.feature.Load
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Dependency
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature

@Feature(
    id = "warp",
    platform = Platform.PAPER,
    dependencies = [
        Dependency(id = "teleport", load = Load.BEFORE, required = true),
        Dependency(id = "menu", load = Load.BEFORE, required = false),
    ],
)
class WarpFeature : PaperFeature() {
}
