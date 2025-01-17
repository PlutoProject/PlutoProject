package plutoproject.feature.paper.warp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import plutoproject.feature.paper.api.warp.Warp
import plutoproject.feature.paper.api.warp.WarpManager
import plutoproject.framework.paper.api.interactive.layout.list.ListMenuModel
import kotlin.math.ceil

private const val PAGE_SIZE = 28

class DefaultSpawnPickerScreenModel : ListMenuModel<Warp>() {
    var isPreferredSet by mutableStateOf(false)
    var preferredSet by mutableStateOf<Warp?>(null)

    override suspend fun fetchPageContents(): List<Warp> {
        val spawns = WarpManager.listSpawns().toList()
            .sortedBy { it.createdAt }
        pageCount = ceil(spawns.size.toDouble() / PAGE_SIZE).toInt()
        return spawns.drop(page * PAGE_SIZE).take(PAGE_SIZE)
    }
}
