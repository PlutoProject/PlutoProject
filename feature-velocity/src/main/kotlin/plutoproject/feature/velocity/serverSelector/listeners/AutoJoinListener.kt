package plutoproject.feature.velocity.serverSelector.listeners

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.feature.common.serverSelector.AutoJoinOptionDescriptor
import plutoproject.feature.common.serverSelector.UserRepository
import plutoproject.framework.common.api.options.OptionsManager
import plutoproject.framework.velocity.util.server
import kotlin.jvm.optionals.getOrNull

@Suppress("UNUSED")
object AutoJoinListener : KoinComponent {
    private val userRepo by inject<UserRepository>()

    @Subscribe(order = PostOrder.FIRST)
    suspend fun PlayerChooseInitialServerEvent.e() {
        val uuid = player.uniqueId
        val options = OptionsManager.getOptions(uuid) ?: return
        val entry = options.getEntry(AutoJoinOptionDescriptor) ?: return
        if (!entry.value) return
        val userModel = userRepo.find(uuid) ?: return
        val registeredServer = server.getServer(userModel.previouslyJoinedServer).getOrNull() ?: return
        setInitialServer(registeredServer)
    }
}
