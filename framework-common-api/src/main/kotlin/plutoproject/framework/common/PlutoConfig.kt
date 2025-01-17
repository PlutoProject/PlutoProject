package plutoproject.framework.common

data class PlutoConfig(
    val serverName: String = "default",
    val feature: Feature = Feature(),
)

data class Feature(
    val enabled: List<String> = listOf(),
    val autoLoad: List<String> = listOf(),
)
