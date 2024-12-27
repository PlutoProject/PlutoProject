package plutoproject.framework.paper.util

import net.minecraft.server.MinecraftServer
import org.bukkit.Server
import org.bukkit.craftbukkit.CraftServer
import org.bukkit.plugin.java.JavaPlugin
import plutoproject.framework.common.util.jvm.findClass

val IS_FOLIA = findClass("io.papermc.paper.threadedregions.RegionizedServer") != null

lateinit var plugin: JavaPlugin
lateinit var server: Server
lateinit var serverThread: Thread

fun isAsync(): Boolean = Thread.currentThread() != serverThread

fun isFoliaOrAsync() = IS_FOLIA || isAsync()

val Thread.isServerThread: Boolean
    get() = this == serverThread

fun Server.toNms(): MinecraftServer = (this as CraftServer).server
