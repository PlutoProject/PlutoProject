package plutoproject.feature.paper.api.randomTeleport

import kotlin.time.Duration

interface Cooldown {
    val isFinished: Boolean
    val duration: Duration
    val remainingSeconds: Long

    fun finish()
}
