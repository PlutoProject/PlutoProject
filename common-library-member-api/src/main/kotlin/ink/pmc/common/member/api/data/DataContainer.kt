package ink.pmc.common.member.api.data

import ink.pmc.common.member.api.Member
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.BsonValue
import java.time.Instant

@Suppress("UNUSED")
interface DataContainer {

    val id: Long
    val owner: Member
    val createdAt: Instant
    val lastModifiedAt: Instant
    val contents: BsonDocument

    operator fun set(key: String, value: Any)

    operator fun get(key: String): Any?

    fun setBson(key: String, value: BsonValue)

    fun getBson(key: String): BsonValue?

    fun remove(key: String)

    fun getString(key: String): String?

    fun getByte(key: String): Byte?

    fun getShort(key: String): Short?

    fun getInt(key: String): Int?

    fun getLong(key: String): Long?

    fun getFloat(key: String): Float?

    fun getDouble(key: String): Double?

    fun getChar(key: String): Char?

    fun getBoolean(key: String): Boolean

    fun <T> getList(key: String): List<*>?

    fun <T> getMap(key: String): Map<String, *>?

    fun contains(key: String): Boolean

}