package plutoproject.framework.paper

import org.koin.dsl.module
import plutoproject.framework.common.config.WorldAliasConfig
import plutoproject.framework.common.getModuleConfig
import plutoproject.framework.common.util.PAPER_FRAMEWORK_RESOURCE_PREFIX
import plutoproject.framework.paper.api.toast.ToastFactory

val FrameworkPaperModule = module {
    single<WorldAliasConfig> { getModuleConfig(PAPER_FRAMEWORK_RESOURCE_PREFIX, "world_alias") }
    single<ToastFactory> { ToastFactoryImpl() }
}
