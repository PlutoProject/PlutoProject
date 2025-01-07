package plutoproject.feature.paper.api.sit

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

val Entity.sitter: Player?
    get() = SitManager.getSitterBySeat(this)

val Entity.isSeat: Boolean
    get() = SitManager.isSeat(this)
