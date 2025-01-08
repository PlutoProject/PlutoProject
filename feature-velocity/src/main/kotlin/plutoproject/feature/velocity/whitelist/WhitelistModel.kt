package plutoproject.feature.velocity.whitelist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import plutoproject.framework.common.util.time.currentTimestampMillis
import java.util.*

fun createWhitelistModel(uuid: UUID, name: String): WhitelistModel {
    return WhitelistModel(uuid.toString(), name, currentTimestampMillis)
}

@Serializable
data class WhitelistModel(
    @SerialName("_id") val id: String,
    var rawName: String,
    val addedAt: Long,
) {
    val name: String = rawName.lowercase()
}
