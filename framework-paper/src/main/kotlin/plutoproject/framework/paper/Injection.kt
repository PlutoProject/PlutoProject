package plutoproject.framework.paper

import org.bukkit.entity.Player
import org.koin.core.qualifier.named
import org.koin.dsl.module
import plutoproject.framework.common.api.bridge.Bridge
import plutoproject.framework.common.config.WorldAliasConfig
import plutoproject.framework.common.getModuleConfig
import plutoproject.framework.common.options.OptionsUpdateNotifier
import plutoproject.framework.common.playerdb.DatabaseNotifier
import plutoproject.framework.common.util.PAPER_FRAMEWORK_RESOURCE_PREFIX
import plutoproject.framework.paper.api.interactive.GuiManager
import plutoproject.framework.paper.api.toast.ToastFactory
import plutoproject.framework.paper.api.toast.ToastRenderer
import plutoproject.framework.paper.bridge.BackendBridge
import plutoproject.framework.paper.interactive.GuiManagerImpl
import plutoproject.framework.paper.options.BackendOptionsUpdateNotifier
import plutoproject.framework.paper.playerdb.BackendDatabaseNotifier
import plutoproject.framework.paper.toast.ToastFactoryImpl
import plutoproject.framework.paper.toast.renderers.NmsToastRenderer

val FrameworkPaperModule = module {
    single<WorldAliasConfig> { getModuleConfig(PAPER_FRAMEWORK_RESOURCE_PREFIX, "world_alias") }
    single<ToastFactory> { ToastFactoryImpl() }
    single<ToastRenderer<Player>>(named("default")) { NmsToastRenderer() }
    single<Bridge> { BackendBridge() }
    single<GuiManager> { GuiManagerImpl() }
    single<OptionsUpdateNotifier> { BackendOptionsUpdateNotifier() }
    single<DatabaseNotifier> { BackendDatabaseNotifier() }
}
