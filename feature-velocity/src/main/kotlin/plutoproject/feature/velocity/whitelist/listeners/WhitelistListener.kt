package plutoproject.feature.velocity.whitelist.listeners

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.feature.velocity.whitelist.repositories.WhitelistRepository
import plutoproject.feature.velocity.whitelist.notWhitelisted

@Suppress("UNUSED")
object WhitelistListener : KoinComponent {
    private val repo by inject<WhitelistRepository>()

    @Subscribe(order = PostOrder.FIRST)
    suspend fun LoginEvent.e() {
        if (!repo.hasById(player.uniqueId)) {
            result = ResultedEvent.ComponentResult.denied(notWhitelisted)
            return
        }
        val model = repo.findById(player.uniqueId) ?: error("Unexpected")
        if (model.rawName == player.username) return
        model.rawName = player.username
        repo.saveOrUpdate(model)
    }
}
