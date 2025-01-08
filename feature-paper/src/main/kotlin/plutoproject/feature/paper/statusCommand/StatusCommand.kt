package plutoproject.feature.paper.statusCommand

import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.newline
import ink.pmc.advkt.component.raw
import ink.pmc.advkt.component.text
import ink.pmc.advkt.send
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.koin.dsl.module
import plutoproject.framework.common.api.feature.Platform
import plutoproject.framework.common.api.feature.annotation.Feature
import plutoproject.framework.common.util.chat.palettes.*
import plutoproject.framework.common.util.config.loadConfig
import plutoproject.framework.common.util.inject.configureKoin
import plutoproject.framework.common.util.roundTo2
import plutoproject.framework.paper.api.feature.PaperFeature
import plutoproject.framework.paper.api.statistic.LoadLevel
import plutoproject.framework.paper.api.statistic.MeasuringTime
import plutoproject.framework.paper.api.statistic.StatisticProvider
import plutoproject.framework.paper.util.command.AnnotationParser
import plutoproject.framework.paper.util.coroutine.withSync
import plutoproject.framework.paper.util.plugin
import plutoproject.framework.paper.util.server

@Feature(
    id = "status_command",
    platform = Platform.PAPER,
)
@Suppress("UNUSED")
class StatusCommand : PaperFeature() {
    private val featureModule = module {
        single<StatusCommandConfig> { loadConfig(saveConfig()) }
    }

    override fun onEnable() {
        configureKoin {
            modules(featureModule)
        }
        AnnotationParser.parse(this)
        server.pluginManager.registerSuspendingEvents(StatusCommandListener, plugin)
    }

    @Command("status|tps|mspt")
    @Permission("hypervisor.status")
    suspend fun CommandSender.status() {
        val entities = withSync<List<Entity>> {
            server.worlds.fold(mutableListOf()) { entities, world ->
                entities.apply { addAll(world.entities) }
            }
        }
        val entityCount = entities.size
        val tickingEntityCount = entities.filter { it.isTicking }.size
        send {
            text("» ") with mochaSubtext0
            text("\uD83D\uDD0D 服务器状态") with mochaFlamingo
            newline()
            text("- ") with mochaSubtext0
            text("TPS：") with mochaText
            raw(getTicksPerSecond())
            newline()
            text("- ") with mochaSubtext0
            text("MSPT：") with mochaText
            raw(getMillsPerTick())
            newline()
            text("- ") with mochaSubtext0
            text("实体数：") with mochaText
            text("$entityCount ") with mochaLavender
            text("(") with mochaText
            text("$tickingEntityCount ") with mochaLavender
            text("个活跃中)") with mochaText
            newline()
            text("- ") with mochaSubtext0
            text("在线人数：") with mochaText
            text(server.onlinePlayers.size) with mochaLavender
            newline()
            newline()
            text("» ") with mochaSubtext0
            text("ℹ 说明") with mochaBlue
            newline()
            val messages = getPromptMessage()
            val last = messages.lastIndex
            getPromptMessage().forEachIndexed { i, c ->
                text("- ") with mochaSubtext0
                raw(c)
                if (i != last) newline()
            }
        }
    }

    private fun getTicksPerSecond(): Component {
        return StatisticProvider.getTicksPerSecond(MeasuringTime.SECONDS_10)!!.let {
            component {
                text(it.roundTo2()) with when {
                    it in 16.0..18.0 -> mochaYellow
                    it < 16.0 -> mochaMaroon
                    else -> mochaGreen
                }
            }
        }
    }

    private fun getMillsPerTick(): Component {
        return component {
            text(
                StatisticProvider.getMillsPerTick(MeasuringTime.SECONDS_10)!!.roundTo2()
            ) with when (StatisticProvider.getLoadLevel()!!) {
                LoadLevel.LOW -> mochaGreen
                LoadLevel.MODERATE -> mochaYellow
                LoadLevel.HIGH -> mochaMaroon
            }
        }
    }

    private fun getPromptMessage(): List<Component> {
        return buildList {
            when (StatisticProvider.getLoadLevel()!!) {
                LoadLevel.LOW -> {
                    add(component {
                        text("服务器目前负载较低") with mochaGreen
                    })
                    add(component {
                        text("可适量开启机器、进行跑图") with mochaGreen
                    })
                }

                LoadLevel.MODERATE -> {
                    add(component {
                        text("服务器目前负载中等") with mochaYellow
                    })
                    add(component {
                        text("建议关闭不在使用的机器、酌情降低跑图速度、离开村民交易所等多实体场景") with mochaYellow
                    })
                }

                LoadLevel.HIGH -> {
                    add(component {
                        text("服务器目前已过载") with mochaMaroon
                    })
                    add(component {
                        text("请关闭正在运行的机器、暂缓跑图、离开村民交易所等多实体场景") with mochaMaroon
                    })
                }
            }
        }
    }
}
