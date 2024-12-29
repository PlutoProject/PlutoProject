package plutoproject.feature.paper.test

import plutoproject.framework.common.api.feature.annotation.Dependency
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.common.api.feature.annotation.Load
import plutoproject.framework.common.api.feature.annotation.Platform
import plutoproject.framework.paper.api.feature.PaperFeature

@Feature(
    id = "test_feature",
    platform = Platform.PAPER,
    dependencies = [
        Dependency(id = "test_dependency_feature", load = Load.AFTER, required = false),
        Dependency(id = "player_teleport", load = Load.AFTER, required = true),
        Dependency(id = "random_teleport", load = Load.BEFORE, required = true),
    ]
)
class TestFeature : PaperFeature() {
    override fun onLoad() {
    }

    override fun onEnable() {
    }

    override fun onReload() {
    }

    override fun onDisable() {
    }
}
