package plutoproject.feature.paper.align

import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser

@Feature(
    id = "align",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class AlignFeature : PaperFeature() {
    override fun onEnable() {
        AnnotationParser.parse(AlignCommand)
    }
}
