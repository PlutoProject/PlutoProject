package plutoproject.feature.paper.afk

import kotlin.time.Duration

data class AfkConfig(
    val idleDuration: Duration = Duration.parse("10m"),
)
