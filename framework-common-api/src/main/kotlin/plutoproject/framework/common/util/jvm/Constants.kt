package plutoproject.framework.common.util.jvm

import net.bytebuddy.ByteBuddy
import net.bytebuddy.agent.ByteBuddyAgent

@Suppress("UNUSED")
val BYTE_BUDDY = ByteBuddy().also {
    ByteBuddyAgent.install()
}
