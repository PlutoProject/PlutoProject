package plutoproject.feature.paper.api.sit

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import plutoproject.framework.common.util.inject.Koin

interface SitManager {
    companion object : SitManager by Koin.get()

    val sitters: Set<Player>
    val seats: Set<Entity>

    fun sit(player: Player, location: Location)

    fun isSitting(player: Player): Boolean

    fun stand(player: Player)

    fun getSeat(player: Player): Entity?

    fun getSitLocation(player: Player): Location?

    fun getSitterByLocation(location: Location): Player?

    fun getSitterBySeat(seat: Entity): Player?

    fun isSeat(entity: Entity): Boolean

    fun isSitLocation(location: Location): Boolean

    fun standAll()
}
