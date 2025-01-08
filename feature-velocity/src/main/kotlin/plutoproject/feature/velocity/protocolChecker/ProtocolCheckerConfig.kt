package plutoproject.feature.velocity.protocolChecker

data class ProtocolCheckerConfig(
    val protocolRange: Pair<Int, Int> = 767 to 767,
    val serverBrand: String? = null,
) {
    val intProtocolRange: IntRange
        get() = protocolRange.first..protocolRange.second
}
