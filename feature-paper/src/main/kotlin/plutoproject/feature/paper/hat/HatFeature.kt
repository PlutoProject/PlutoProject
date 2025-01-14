package plutoproject.feature.paper.hat

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser

@Feature(
    id = "hat",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class HatFeature : PaperFeature() {
    override fun onEnable() {
        AnnotationParser.parse(HatCommand)
    }
}
