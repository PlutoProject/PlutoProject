package ink.pmc.framework.bridge.backend.listeners

import ink.pmc.framework.bridge.backend.bridgeStub
import ink.pmc.framework.bridge.backend.server.localServer
import ink.pmc.framework.bridge.backend.world.BackendLocalWorld
import ink.pmc.framework.bridge.backend.world.getBridge
import plutoproject.framework.common.bridge.checkCommonResult
import ink.pmc.framework.bridge.proto.worldLoad
import plutoproject.framework.common.bridge.throwLocalWorldNotFound
import plutoproject.framework.common.bridge.warn
import plutoproject.framework.common.bridge.world.createInfo
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.world.SpawnChangeEvent
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.event.world.WorldUnloadEvent

@Suppress("UnusedReceiverParameter")
object BridgeWorldListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    suspend fun WorldLoadEvent.e() {
        val localWorld = BackendLocalWorld(world, localServer)
        localServer.worlds.add(localWorld)
        val result = bridgeStub.updateWorldInfo(localWorld.createInfo())
        checkCommonResult(result)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    suspend fun SpawnChangeEvent.e() {
        val localWorld = world.getBridge()
            ?: return warn { throwLocalWorldNotFound(world.name) }
        val result = bridgeStub.updateWorldInfo(localWorld.createInfo())
        checkCommonResult(result)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    suspend fun WorldUnloadEvent.e() {
        val localWorld = localServer.getWorld(world.name)
            ?: return warn { throwLocalWorldNotFound(world.name) }
        localServer.worlds.remove(localWorld)
        val result = bridgeStub.unloadWorld(worldLoad {
            server = localServer.id
            world = localWorld.name
        })
        checkCommonResult(result)
    }
}