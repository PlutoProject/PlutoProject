package plutoproject.framework.paper.util.entity.player

import net.minecraft.network.protocol.Packet
import org.bukkit.entity.Player

fun Player.sendPacket(packet: Packet<*>) {
    toNmsPlayer().connection.send(packet)
}
