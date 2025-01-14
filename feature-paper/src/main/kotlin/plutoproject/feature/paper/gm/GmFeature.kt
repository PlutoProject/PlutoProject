package plutoproject.feature.paper.gm

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser

@Feature(
    id = "gm",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class GmFeature : PaperFeature() {
    override fun onEnable() {
        AnnotationParser.parse(GmCommand)
    }
}
