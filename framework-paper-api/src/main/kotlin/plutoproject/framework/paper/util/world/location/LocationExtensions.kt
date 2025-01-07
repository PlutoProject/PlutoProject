package plutoproject.framework.paper.util.world.location

import org.bukkit.Location

// 不知道这个函数叫什么好，先这样吧
fun Location.viewAligned(): Location {
    val rawLocation = clone().toBlockLocation()
    rawLocation.yaw = 0F
    rawLocation.pitch = 0F
    return rawLocation
}
