package plutoproject.framework.paper.util

import net.minecraft.server.MinecraftServer
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.craftbukkit.CraftServer
import org.bukkit.plugin.java.JavaPlugin

lateinit var THREAD: Thread
val PLUGIN: JavaPlugin = Bukkit.getPluginManager().getPlugin("PlutoProject") as JavaPlugin
val SERVER: Server = Bukkit.getServer()

val isFoliaEnvironment = try {
    Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
    true
} catch (e: Exception) {
    false
}

val isAsync: Boolean
    get() = Thread.currentThread() != THREAD

val isFoliaEnvironmentOrAsync: Boolean
    get() = isFoliaEnvironment || isAsync

val Thread.isServerThread: Boolean
    get() = this == THREAD

fun Server.toNms(): MinecraftServer = (this as CraftServer).server
