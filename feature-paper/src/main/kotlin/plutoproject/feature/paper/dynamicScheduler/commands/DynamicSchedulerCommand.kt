package plutoproject.feature.paper.dynamicScheduler.commands

import ink.pmc.advkt.component.newline
import ink.pmc.advkt.component.raw
import ink.pmc.advkt.component.text
import ink.pmc.advkt.send
import net.kyori.adventure.text.Component
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.entity.SpawnCategory
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import plutoproject.feature.paper.api.dynamicScheduler.DynamicScheduler
import plutoproject.framework.common.util.chat.palettes.*
import plutoproject.framework.common.util.network.toHostPortString

@Suppress("UNUSED")
object DynamicSchedulerCommand {
    @Command("dynamic_scheduler")
    @Permission("hypervisor.dynamic_scheduler")
    fun CommandSender.root() {
        send {
            text("动态资源调度 ") with mochaText
            if (DynamicScheduler.isRunning) {
                text("运行中") with mochaGreen
            } else {
                text("已停止") with mochaMaroon
            }
        }
    }

    @Command("dynamic_scheduler start")
    @Permission("hypervisor.dynamic_scheduler")
    fun CommandSender.start() {
        if (DynamicScheduler.isRunning) {
            send {
                text("动态资源调度已运行") with mochaMaroon
            }
            return
        }
        DynamicScheduler.start()
        send {
            text("已启动动态资源调度后台任务") with mochaGreen
        }
    }

    @Command("dynamic_scheduler stop")
    @Permission("hypervisor.dynamic_scheduler")
    fun CommandSender.stop() {
        if (!DynamicScheduler.isRunning) {
            send {
                text("动态资源调度未运行") with mochaMaroon
            }
            return
        }
        DynamicScheduler.stop()
        send {
            text("已停止动态资源调度后台任务") with mochaMaroon
            newline()
            text("部分由该功能接管的配置项无法自动恢复，请重启服务器") with mochaSubtext0
        }
    }

    @Command("dynamic_scheduler status [world]")
    @Permission("hypervisor.dynamic_scheduler")
    fun CommandSender.status(@Argument("world") world: World?) {
        val actualWorld = world ?: if (this is Player) this.world else {
            send {
                text("请指定一个世界") with mochaMaroon
            }
            return
        }
        send {
            text("世界 ") with mochaText
            text("${actualWorld.name} ") with mochaFlamingo
            text("当前信息：") with mochaText
            newline()
            text("- ") with mochaSubtext0
            text("模拟距离：") with mochaText
            text(actualWorld.simulationDistance) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("生成上限：") with mochaText
            newline()
            SpawnCategory.entries.forEach { category ->
                if (category == SpawnCategory.MISC) return@forEach
                text("  - ") with mochaSubtext0
                text("$category: ") with mochaText
                text(actualWorld.getSpawnLimit(category)) with mochaGreen
                if (SpawnCategory.entries.dropLast(1).last() == category) return@forEach
                newline()
            }
            newline()
            text("- ") with mochaSubtext0
            text("生成频率：") with mochaText
            newline()
            SpawnCategory.entries.forEach { category ->
                if (category == SpawnCategory.MISC) return@forEach
                text("  - ") with mochaSubtext0
                text("$category: ") with mochaText
                text(actualWorld.getTicksPerSpawns(category)) with mochaGreen
                if (SpawnCategory.entries.dropLast(1).last() == category) return@forEach
                newline()
            }
        }
    }

    @Command("dynamic_scheduler view_distance [player]")
    @Permission("hypervisor.dynamic_scheduler")
    fun CommandSender.viewDistance(@Argument("player") player: Player?) {
        val actualPlayer = player ?: if (this is Player) this else {
            send {
                text("请指定一个玩家") with mochaMaroon
            }
            return
        }
        if (!DynamicScheduler.viewDistanceEnabled) {
            send {
                text("动态视距未启用") with mochaMaroon
            }
            return
        }
        send {
            text("玩家 ") with mochaText
            text("${actualPlayer.name} ") with mochaFlamingo
            text("当前信息：") with mochaText
            newline()
            text("- ") with mochaSubtext0
            text("世界视距：") with mochaText
            text(actualPlayer.world.viewDistance) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("视距：") with mochaText
            text(actualPlayer.viewDistance) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("发送视距：") with mochaText
            text(actualPlayer.sendViewDistance) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("模拟距离：") with mochaText
            text(actualPlayer.simulationDistance) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("延迟：") with mochaText
            text(actualPlayer.ping) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("连接地址：") with mochaText
            text(actualPlayer.virtualHost?.toHostPortString().toString()) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("动态视距状态：") with mochaText
            text(DynamicScheduler.getViewDistanceLocally(actualPlayer).toString()) with mochaGreen
            newline()
            text("- ") with mochaSubtext0
            text("是否在选项中开启：") with mochaText
            raw(
                if (DynamicScheduler.getViewDistance(actualPlayer)) {
                    Component.text("是").color(mochaGreen)
                } else {
                    Component.text("否").color(mochaMaroon)
                }
            )
        }
    }
}
