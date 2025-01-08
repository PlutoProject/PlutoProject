package plutoproject.feature.paper.suicide

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser

@Feature(
    id = "suicide",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class SuicideFeature : PaperFeature() {
    override fun onEnable() {
        AnnotationParser.parse(SuicideCommand)
    }
}
