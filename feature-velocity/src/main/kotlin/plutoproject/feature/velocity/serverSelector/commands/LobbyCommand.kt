package plutoproject.feature.velocity.serverSelector.commands

import com.velocitypowered.api.command.CommandSource
import ink.pmc.advkt.component.italic
import ink.pmc.advkt.component.text
import ink.pmc.advkt.send
import ink.pmc.advkt.title.*
import net.kyori.adventure.util.Ticks
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.feature.common.serverSelector.TELEPORT_FAILED_SOUND
import plutoproject.feature.common.serverSelector.TELEPORT_SUCCEED_SOUND
import plutoproject.feature.velocity.serverSelector.VelocityServerSelectorConfig
import plutoproject.framework.common.util.chat.palettes.mochaMaroon
import plutoproject.framework.common.util.chat.palettes.mochaText
import plutoproject.framework.velocity.api.bridge.player.toBridge
import plutoproject.framework.velocity.util.command.ensurePlayer
import plutoproject.framework.velocity.util.switchServer
import kotlin.jvm.optionals.getOrNull

object LobbyCommand : KoinComponent {
    private val config by inject<VelocityServerSelectorConfig>()

    @Command("lobby|hub")
    @Permission("server_selector.command")
    suspend fun CommandSource.lobby() = ensurePlayer() {
        if (currentServer.getOrNull()?.serverInfo?.name == config.transferServer) {
            send {
                text("无法在此处使用该命令") with mochaMaroon without italic()
            }
            return@ensurePlayer
        }
        if (switchServer(config.transferServer).isSuccessful) {
            toBridge().playSound(TELEPORT_SUCCEED_SOUND)
            return@ensurePlayer
        }
        val bridge = toBridge()
        bridge.showTitle {
            times {
                fadeIn(Ticks.duration(5))
                stay(Ticks.duration(35))
                fadeOut(Ticks.duration(20))
            }
            mainTitle {
                text("传送失败") with mochaMaroon
            }
            subTitle {
                text("请再试一次") with mochaText
            }
        }
        bridge.playSound(TELEPORT_FAILED_SOUND)
    }
}
