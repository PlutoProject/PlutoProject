package plutoproject.framework.common.bridge

import ink.pmc.advkt.component.*
import ink.pmc.advkt.sound.key
import ink.pmc.advkt.sound.pitch
import ink.pmc.advkt.sound.volume
import ink.pmc.advkt.title.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import plutoproject.framework.common.api.bridge.Bridge
import plutoproject.framework.common.api.bridge.player.BridgePlayer
import plutoproject.framework.common.api.bridge.server.BridgeServer
import plutoproject.framework.common.api.bridge.world.BridgeLocation
import plutoproject.framework.common.api.bridge.world.BridgeWorld
import plutoproject.framework.common.util.chat.palettes.*
import kotlin.time.Duration

@Suppress("UNUSED")
abstract class AbstractBridgeCommand<T> {
    abstract fun T.message(component: Component)

    private fun T.send(component: RootComponentKt.() -> Unit) {
        message(RootComponentKt().apply(component).build())
    }

    private fun getServerListMessage(list: Collection<BridgeServer>): Component {
        return component {
            list.forEachIndexed { index, it ->
                text("- ") with MOCHA_SUBTEXT_0
                text("${it.id}: ") with MOCHA_TEXT
                if (it.isOnline) {
                    text("在线 ") with MOCHA_GREEN
                } else {
                    text("离线 ") with MOCHA_GREEN
                }
                text("状态 ") with MOCHA_TEXT
                text("${it.state}") with MOCHA_LAVENDER
                text(", 类型 ") with MOCHA_TEXT
                text("${it.type}") with MOCHA_LAVENDER
                if (it.group != null) {
                    text(", 组 ") with MOCHA_TEXT
                    text(it.group!!.id) with MOCHA_LAVENDER
                }
                text(", 玩家 ") with MOCHA_TEXT
                text(it.playerCount) with MOCHA_LAVENDER
                if (!it.type.isProxy) {
                    text(", 世界 ") with MOCHA_TEXT
                    text(it.worlds.size) with MOCHA_LAVENDER
                }
                if (index != list.indices.last) {
                    newline()
                }
            }
        }
    }

    fun T.listServers() {
        send {
            text("» ") with MOCHA_SUBTEXT_0
            text("已注册的服务器") with MOCHA_FLAMINGO
            newline()
            raw(getServerListMessage(Bridge.servers))
        }
    }

    private suspend fun getPlayerListMessage(list: Collection<BridgePlayer>): Component {
        var component = Component.empty()
        list.forEachIndexed { index, it ->
            var loc: BridgeLocation? = null
            if (!it.serverType.isProxy) {
                loc = it.location.await()
            }
            component = component.append(component {
                text("- ") with MOCHA_SUBTEXT_0
                text("${it.name}: ") with MOCHA_TEXT
                text("服务器 ") with MOCHA_TEXT
                text(it.server.id) with MOCHA_LAVENDER
                text(", 状态 ") with MOCHA_TEXT
                text("${it.serverState}") with MOCHA_LAVENDER
                text(", 类型 ") with MOCHA_TEXT
                text("${it.serverType}") with MOCHA_LAVENDER
                if (loc != null) {
                    text(", 位置 ") with MOCHA_TEXT
                    text("${loc.world.aliasOrName} ${loc.x.toInt()}, ${loc.y.toInt()}, ${loc.z.toInt()}") with MOCHA_LAVENDER
                }
                if (index != list.indices.last) {
                    newline()
                }
            })
        }
        return component
    }

    suspend fun T.listPlayers() {
        send {
            text("» ") with MOCHA_SUBTEXT_0
            text("已添加的玩家") with MOCHA_FLAMINGO
        }
        val list = getPlayerListMessage(Bridge.players)
        send {
            raw(list)
        }
    }

    suspend fun T.teleport(
        player: BridgePlayer,
        other: BridgePlayer
    ) {
        player.teleport(other)
        send {
            text("已将 ") with MOCHA_PINK
            text("${player.name} ") with MOCHA_TEXT
            text("传送到 ") with MOCHA_PINK
            text(other.name) with MOCHA_TEXT
        }
    }

    suspend fun T.sendMessage(
        player: BridgePlayer,
        message: Component
    ) {
        player.sendMessage(message)
        send {
            text("已向 ") with MOCHA_PINK
            text("${player.name} ") with MOCHA_TEXT
            text("发送 ") with MOCHA_PINK
            raw(message) with MOCHA_SUBTEXT_0
        }
    }

    suspend fun T.showTitle(
        player: BridgePlayer,
        mainTitle: Component,
        subTitle: Component,
        fadeIn: Duration,
        stay: Duration,
        fadeOut: Duration
    ) {
        player.showTitle {
            times {
                fadeIn(fadeIn)
                stay(stay)
                fadeOut(fadeOut)
            }
            mainTitle(mainTitle)
            subTitle(subTitle)
        }
        send {
            text("已向 ") with MOCHA_PINK
            text("${player.name} ") with MOCHA_TEXT
            text("发送标题") with MOCHA_PINK
        }
    }

    suspend fun T.playSound(
        player: BridgePlayer,
        key: String,
        volume: Float,
        pitch: Float
    ) {
        player.playSound {
            key(Key.key(key))
            volume(volume)
            pitch(pitch)
        }
        send {
            text("已向 ") with MOCHA_PINK
            text("${player.name} ") with MOCHA_TEXT
            text("播放声音") with MOCHA_PINK
        }
    }

    suspend fun T.performCommand(
        player: BridgePlayer,
        command: String,
    ) {
        player.performCommand(command)
        send {
            text("正在使 ") with MOCHA_PINK
            text("${player.name} ") with MOCHA_TEXT
            text("执行命令 ") with MOCHA_PINK
            text(command) with MOCHA_SUBTEXT_0
        }
    }

    private fun getWorldListMessage(list: Collection<BridgeWorld>): Component {
        return component {
            list.forEachIndexed { index, it ->
                text("- ") with MOCHA_SUBTEXT_0
                text("${it.name}: ") with MOCHA_TEXT
                text("服务器 ") with MOCHA_TEXT
                text(it.server.id) with MOCHA_LAVENDER
                text(", 状态 ") with MOCHA_TEXT
                text("${it.serverState}") with MOCHA_LAVENDER
                text(", 类型 ") with MOCHA_TEXT
                text("${it.serverType}") with MOCHA_LAVENDER
                text(", 玩家 ") with MOCHA_TEXT
                text(it.playerCount) with MOCHA_LAVENDER
                text(", 出生点 ") with MOCHA_TEXT
                text("${it.spawnPoint.world.aliasOrName} ${it.spawnPoint.x.toInt()}, ${it.spawnPoint.y.toInt()}, ${it.spawnPoint.z.toInt()}") with MOCHA_LAVENDER
                if (index != list.indices.last) {
                    newline()
                }
            }
        }
    }

    fun T.listWorlds() {
        send {
            text("» ") with MOCHA_SUBTEXT_0
            text("已添加的世界") with MOCHA_FLAMINGO
            newline()
            raw(getWorldListMessage(Bridge.worlds))
        }
    }
}
