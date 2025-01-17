package ink.pmc.essentials.config

import org.bukkit.Material
import org.bukkit.block.Biome
import kotlin.time.Duration

data class EssentialsConfig(
    val serverName: String,
    val teleport: Teleport,
    val randomTeleport: RandomTeleport,
    val back: Back,
    val home: Home,
    val warp: Warp,
    val afk: Afk,
    val containerProtection: ContainerProtection,
    val action: Action,
    val item: Item,
    val recipe: Recipe,
    val join: Join,
    val disableJoinQuitMessage: DisableJoinQuitMessage = DisableJoinQuitMessage(),
    val demoWorld: DemoWorld,
    val head: Head = Head(),
)

data class Back(
    val enabled: Boolean,
    val blacklistedWorlds: List<String>
)

data class Home(
    val enabled: Boolean,
    val maxHomes: Int,
    val nameLengthLimit: Int,
    val blacklistedWorlds: List<String>
)

data class Warp(
    val enabled: Boolean,
    val nameLengthLimit: Int,
    val blacklistedWorlds: List<String>
)

data class Afk(
    val enabled: Boolean,
    val idleDuration: Duration
)

data class ContainerProtection(
    val enabled: Boolean,
    val itemframe: Boolean,
    val lectern: Boolean
)

data class Action(
    val enabled: Boolean,
)

data class Item(
    val enabled: Boolean,
)

data class Recipe(
    val enabled: Boolean,
    val autoUnlock: Boolean,
    val vanillaExtend: Boolean
)

data class Join(
    val enabled: Boolean,
)

data class DisableJoinQuitMessage(
    val enabled: Boolean = true,
)

data class DemoWorld(
    val enabled: Boolean,
    val worlds: Map<String, DemoWorldOptions>
)

data class DemoWorldOptions(
    val spawnpoint: Spawnpoint,
    val teleportHeight: Int
)

data class Spawnpoint(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float = 0.0F,
    val pitch: Float = 0.0F,
)

data class Head(
    val enabled: Boolean = true,
    val cost: Double = 3.0
)
