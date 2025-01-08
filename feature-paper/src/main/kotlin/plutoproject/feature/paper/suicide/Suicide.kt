package plutoproject.feature.paper.suicide

import plutoproject.feature.paper.suicide.commands.SuicideCommand
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.util.command.AnnotationParser

@Feature(
    id = "suicide",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class Suicide : PaperFeature() {
    override fun onEnable() {
        AnnotationParser.parse(SuicideCommand)
    }
}
