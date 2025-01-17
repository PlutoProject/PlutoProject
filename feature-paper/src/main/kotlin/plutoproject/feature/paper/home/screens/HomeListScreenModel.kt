package plutoproject.feature.paper.home.screens

import org.bukkit.OfflinePlayer
import plutoproject.feature.paper.api.home.Home
import plutoproject.feature.paper.api.home.HomeManager
import plutoproject.framework.paper.api.interactive.layout.list.ListMenuModel
import kotlin.math.ceil

private const val PAGE_SIZE = 28

class HomeListScreenModel(private val viewing: OfflinePlayer) : ListMenuModel<Home>() {
    override suspend fun fetchPageContents(): List<Home> {
        val homes = HomeManager.list(viewing).toList()
            .sortedBy {
                when {
                    it.isPreferred -> 0
                    it.isStarred -> 1
                    else -> 2
                }
            }
        pageCount = ceil(homes.size.toDouble() / PAGE_SIZE).toInt()
        return homes.drop(page * PAGE_SIZE).take(PAGE_SIZE)
    }
}
