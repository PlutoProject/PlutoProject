package plutoproject.framework.common.util.audience

import ink.pmc.advkt.component.newline
import ink.pmc.advkt.component.text
import ink.pmc.advkt.send
import net.kyori.adventure.audience.Audience
import plutoproject.framework.common.util.chat.palette.MOCHA_MAROON
import plutoproject.framework.common.util.chat.palette.MOCHA_SUBTEXT_0
import plutoproject.framework.common.util.logger.logger
import java.util.logging.Level

inline fun <T> T.catchException(
    audience: Audience? = null,
    onFailure: Audience.(Throwable) -> Unit = {},
    failureLog: String = "Exception caught",
    action: T.() -> Unit
) {
    runCatching {
        action()
    }.onFailure {
        audience?.onFailure(it)
        logger.log(Level.SEVERE, failureLog, it)
    }
}

inline fun <T> T.catchExceptionInteraction(audience: Audience? = null, action: T.() -> Unit) {
    catchException(
        audience = audience,
        onFailure = {
            send {
                text("处理交互时出现服务器内部错误") with MOCHA_MAROON
                newline()
                text("请将其反馈给管理组以便我们尽快解决") with MOCHA_SUBTEXT_0
            }
        },
        failureLog = "Exception caught while handling interaction"
    ) {
        action()
    }
}
