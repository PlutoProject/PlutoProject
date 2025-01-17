package plutoproject.feature.paper.api.randomTeleport

import org.bukkit.Location

data class RandomResult(
    val attempts: Int,
    val location: Location?
)
