package plutoproject.framework.common.bridge

import plutoproject.framework.common.config.BridgeConfig
import plutoproject.framework.common.util.inject.koin
import java.util.logging.Level

private val config by koin.inject<BridgeConfig>()

fun debugInfo(message: String) {
    if (!config.debug) return
    logger.info(message)
}

fun debugWarn(message: String, e: Throwable? = null) {
    if (!config.debug) return
    if (e != null) {
        logger.log(Level.WARNING, message, e)
    } else {
        logger.warning(message)
    }
}

fun debugError(message: String, e: Throwable? = null) {
    if (!config.debug) return
    if (e != null) {
        logger.log(Level.SEVERE, message, e)
    } else {
        logger.severe(message)
    }
}
