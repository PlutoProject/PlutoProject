package plutoproject.feature.paper.home

data class HomeConfig(
    val maxHomes: Int = 20,
    val nameLengthLimit: Int = 16,
    val blacklistedWorlds: List<String> = emptyList()
)
