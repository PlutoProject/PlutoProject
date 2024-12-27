package ink.pmc.framework.world

import ink.pmc.framework.FrameworkConfig
import org.bukkit.World
import org.koin.java.KoinJavaComponent.getKoin

val World.alias: String?
    get() = getKoin().get<FrameworkConfig>().worldAliases[name]

inline val World.aliasOrName: String
    get() = alias ?: name