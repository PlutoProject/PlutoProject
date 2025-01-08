package plutoproject.feature.paper.elevator

import ink.pmc.advkt.sound.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

val elevatorWorking = sound {
    key(Key.key("entity.iron_golem.attack"))
    source(Sound.Source.BLOCK)
    volume(1f)
    pitch(1f)
}
