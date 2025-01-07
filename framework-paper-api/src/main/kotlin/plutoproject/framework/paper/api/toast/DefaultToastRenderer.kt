package plutoproject.framework.paper.api.toast

import org.bukkit.entity.Player
import org.koin.core.qualifier.named
import plutoproject.framework.common.util.inject.Koin

object DefaultToastRenderer : ToastRenderer<Player> by Koin.get(named("default"))
