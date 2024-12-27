package plutoproject.framework.common.util.data.json

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

inline fun <reified T> JsonElement.toObject(gson: Gson = GSON): T {
    return gson.fromJson(this, T::class.java)
}

inline fun <reified T> JsonObject.toObject(gson: Gson = GSON): T {
    return gson.fromJson(this, T::class.java)
}

inline fun <reified T> String.convertJsonToObject(gson: Gson = GSON): T {
    return gson.fromJson(this, T::class.java)
}

fun <T> String.convertJsonToObject(type: Class<T>, gson: Gson = GSON): T {
    return gson.fromJson(this, type)
}
