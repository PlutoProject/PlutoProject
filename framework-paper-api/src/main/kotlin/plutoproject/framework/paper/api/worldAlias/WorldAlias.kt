package plutoproject.framework.paper.api.worldAlias

import org.bukkit.World
import plutoproject.framework.common.util.inject.inlinedGet

interface WorldAlias {
    companion object : WorldAlias by inlinedGet<WorldAlias>()

    fun getAlias(world: World): String?

    fun getAliasOrName(world: World): String
}
