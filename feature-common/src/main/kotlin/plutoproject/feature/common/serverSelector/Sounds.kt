package plutoproject.feature.common.serverSelector

import ink.pmc.advkt.sound.key
import ink.pmc.advkt.sound.sound
import net.kyori.adventure.key.Key

val TELEPORT_SUCCEED_SOUND = sound {
    key(Key.key("entity.enderman.teleport"))
}

val TELEPORT_FAILED_SOUND = sound {
    key(Key.key("block.amethyst_cluster.break"))
}
