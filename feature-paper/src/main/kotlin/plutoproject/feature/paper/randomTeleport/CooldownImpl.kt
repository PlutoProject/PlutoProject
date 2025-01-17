package plutoproject.feature.paper.randomTeleport

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import plutoproject.feature.paper.api.randomTeleport.Cooldown
import plutoproject.framework.common.util.data.flow.getValue
import plutoproject.framework.common.util.data.flow.setValue
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CooldownImpl(override val duration: Duration, private val finishCallback: () -> Unit) : Cooldown, CoroutineScope {
    private var passedSeconds by MutableStateFlow(0)
    override val coroutineContext: CoroutineContext = Dispatchers.Default
    override var isFinished: Boolean by MutableStateFlow(false)
    override var remainingSeconds: Long by MutableStateFlow(duration.inWholeSeconds)

    init {
        launch {
            while (true) {
                delay(1.seconds)
                if (isFinished) break
                remainingSeconds = duration.inWholeSeconds - (++passedSeconds)
            }
        }
        launch {
            delay(duration)
            finish()
        }
    }

    override fun finish() {
        isFinished = true
        runCatching {
            cancel()
        }
        finishCallback()
    }
}
