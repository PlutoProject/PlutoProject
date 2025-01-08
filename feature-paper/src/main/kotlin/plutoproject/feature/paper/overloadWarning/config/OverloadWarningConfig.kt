package plutoproject.feature.paper.overloadWarning.config

import kotlin.time.Duration

data class OverloadWarningConfig(
    val cyclePeriod: Duration = Duration.parse("5m")
)
