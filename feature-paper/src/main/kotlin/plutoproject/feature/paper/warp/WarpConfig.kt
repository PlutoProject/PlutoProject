package plutoproject.feature.paper.warp

data class WarpConfig(
    val nameLengthLimit: Int = 32,
    val blacklistedWorlds: List<String> = emptyList(),
)
