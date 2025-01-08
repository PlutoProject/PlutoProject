package plutoproject.feature.paper.api.menu

import plutoproject.framework.common.api.feature.FeatureManager

inline val isMenuAvailable: Boolean
    get() = FeatureManager.isEnabled("menu")
