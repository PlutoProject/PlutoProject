package plutoproject.framework.paper.util.world.location

import org.bukkit.Location

/**
 * 一个不可变的数据类，用于表示一个于世界无关的位置。
 */
data class Position2D(val x: Double, val z: Double) {
    constructor(location: Location) : this(location.x, location.z)

    fun add(x: Double, z: Double): Position2D = copy(
        x = this.x + x,
        z = this.z + z,
    )

    fun subtract(x: Double, z: Double): Position2D = copy(
        x = this.x - x,
        z = this.z - z,
    )
}
