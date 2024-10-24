package ink.pmc.framework.commands

import com.velocitypowered.api.command.CommandSource
import ink.pmc.advkt.component.text
import ink.pmc.advkt.send
import ink.pmc.rpc.api.RpcServer
import ink.pmc.utils.visual.mochaFlamingo
import ink.pmc.utils.visual.mochaMaroon
import ink.pmc.utils.visual.mochaSubtext0
import ink.pmc.utils.visual.mochaText
import org.incendo.cloud.annotations.Command

@Suppress("UNUSED")
object RpcCommand {
    @Command("rpc")
    fun rpcCommand(sender: CommandSource) {
        if (RpcServer.server.services.isEmpty()) {
            sender.send {
                text("暂无注册的服务") with mochaMaroon
            }
            return
        }
        sender.send {
            text("已在 gRPC 服务端注册的服务: ") with mochaFlamingo
        }
        RpcServer.server.services.forEach { service ->
            val name = service.serviceDescriptor.name
            sender.send {
                text("  - ") with mochaSubtext0
                text(name) with mochaText
            }
        }
    }
}