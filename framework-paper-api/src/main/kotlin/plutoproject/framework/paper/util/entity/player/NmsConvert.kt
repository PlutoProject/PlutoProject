package plutoproject.framework.paper.util.entity.player

import net.minecraft.server.level.ServerPlayer
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.toNmsPlayer(): ServerPlayer = (this as CraftPlayer).handle
