package plutoproject.framework.velocity

import org.koin.dsl.module
import plutoproject.framework.common.api.bridge.Bridge
import plutoproject.framework.common.options.OptionsUpdateNotifier
import plutoproject.framework.common.playerdb.DatabaseNotifier
import plutoproject.framework.velocity.bridge.ProxyBridge
import plutoproject.framework.velocity.options.OptionsUpdateNotifier as VelocityOptionsUpdateNotifier
import plutoproject.framework.velocity.playerdb.DatabaseNotifier as VelocityDatabaseNotifier

val FrameworkVelocityModule = module {
    single<OptionsUpdateNotifier> { VelocityOptionsUpdateNotifier() }
    single<DatabaseNotifier> { VelocityDatabaseNotifier() }
    single<Bridge> { ProxyBridge() }
}
