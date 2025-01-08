package plutoproject.feature.velocity.protocolChecker

import com.velocitypowered.api.network.ProtocolVersion

internal fun Int.toGameVersions(): List<String> = ProtocolVersion.getProtocolVersion(this).versionsSupportedBy
