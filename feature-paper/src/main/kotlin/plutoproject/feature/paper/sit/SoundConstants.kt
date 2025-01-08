package plutoproject.feature.paper.sit

import ink.pmc.advkt.sound.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

val ALREADY_OTHER_PLAYER_SIT_DOWN_SOUND = sound {
    key(Key.key("block.note_block.hat"))
    source(Sound.Source.BLOCK)
    volume(1f)
    pitch(1f)
}
