package plutoproject.framework.common.config

data class PlutoConfig(
    val feature: Feature = Feature(),
)

data class Feature(
    val enabled: List<String> = listOf(),
    val autoLoad: List<String> = listOf(),
)
