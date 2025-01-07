package plutoproject.feature.paper.api.sit

import org.bukkit.Location
import org.bukkit.entity.Player

val Location.sitter: Player?
    get() = SitManager.getSitterByLocation(this)

val Location.isSitLocation: Boolean
    get() = SitManager.isSitLocation(this)
