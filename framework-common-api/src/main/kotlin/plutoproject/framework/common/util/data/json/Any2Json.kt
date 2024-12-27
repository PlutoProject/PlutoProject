package plutoproject.framework.common.util.data.json

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun Any.toJsonObject(gson: Gson = GSON): JsonObject {
    return gson.toJsonTree(this).asJsonObject
}

fun Any.toJsonElement(gson: Gson = GSON): JsonElement {
    return gson.toJsonTree(this)
}

fun Any.toJsonString(gson: Gson = GSON): String {
    return gson.toJson(this)
}
