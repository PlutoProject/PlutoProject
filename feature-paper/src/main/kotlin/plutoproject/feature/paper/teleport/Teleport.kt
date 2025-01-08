package plutoproject.feature.paper.teleport

import plutoproject.framework.common.api.feature.Load
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Dependency
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature

@Feature(
    id = "teleport",
    platform = Platform.PAPER,
    dependencies = [
        Dependency(id = "menu", load = Load.BEFORE, required = false),
    ],
)
class Teleport : PaperFeature() {
}
