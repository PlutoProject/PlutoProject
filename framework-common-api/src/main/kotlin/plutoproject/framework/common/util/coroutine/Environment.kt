package plutoproject.framework.common.util.coroutine

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import java.util.logging.Logger

private val LOGGER = Logger.getLogger("framework/common/util/coroutine/Environment")

@OptIn(DelicateCoroutinesApi::class)
fun shutdownCoroutineEnvironment() {
    if (COROUTINE_SCOPE.isActive) {
        COROUTINE_SCOPE.cancel()
    }
    Dispatchers.shutdown()
    waitFinalize()
}

private fun waitFinalize() {
    LOGGER.info("Wait 1s for finalizing...")
    Thread.sleep(1000)
}
