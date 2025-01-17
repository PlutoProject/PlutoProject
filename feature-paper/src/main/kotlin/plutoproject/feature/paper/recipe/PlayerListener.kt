package plutoproject.feature.paper.recipe

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.feature.paper.recipe.recipes.vanillaExtendRecipes

@Suppress("UNUSED")
object PlayerListener : Listener, KoinComponent {
    private val config by inject<RecipeConfig>()

    @EventHandler
    fun PlayerJoinEvent.e() {
        if (!config.autoUnlock) return
        player.discoverRecipes(vanillaExtendRecipes.map { it.key })
    }
}
