package plutoproject.feature.paper.api.sit

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

fun Player.sit(location: Location) = SitManager.sit(this, location)

val Player.seat: Entity?
    get() = SitManager.getSeat(this)

val Player.sitLocation: Location?
    get() = SitManager.getSitLocation(this)

val Player.isSitting: Boolean
    get() = SitManager.isSitting(this)

fun Player.stand() = SitManager.stand(this)
