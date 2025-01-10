package plutoproject.feature.common.serverSelector

import org.koin.dsl.module
import plutoproject.framework.common.api.provider.Provider
import plutoproject.framework.common.api.provider.getCollection

val CommonFeatureModule = module {
    single<UserRepository> { UserRepository(Provider.getCollection("server_selector_users")) }
}
