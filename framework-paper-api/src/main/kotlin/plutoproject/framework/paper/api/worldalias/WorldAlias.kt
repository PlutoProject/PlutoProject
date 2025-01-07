package plutoproject.framework.paper.api.worldalias

import org.bukkit.World
import plutoproject.framework.common.util.inject.Koin

interface WorldAlias {
    companion object : WorldAlias by Koin.get()

    fun getAlias(world: World): String?

    fun getAliasOrName(world: World): String
}
