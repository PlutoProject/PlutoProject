package plutoproject.feature.velocity.playerConutLimiter

data class LimiterConfig(
    val forwardPlayerList: Boolean = true,
    val samplePlayersCount: Int = 20,
    val maxPlayerCount: Int = 100,
)
