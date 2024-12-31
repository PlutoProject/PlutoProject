package plutoproject.framework.common.feature.metadata

import kotlinx.serialization.Serializable
import plutoproject.framework.common.api.feature.annotation.Load

@Serializable
data class DependencyMetadata(
    val id: String,
    val load: Load,
    val required: Boolean
)
