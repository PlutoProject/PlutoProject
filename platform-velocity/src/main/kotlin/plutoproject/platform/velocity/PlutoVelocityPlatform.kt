package plutoproject.platform.velocity

import com.velocitypowered.api.proxy.ProxyServer
import plutoproject.framework.common.FrameworkCommonModule
import plutoproject.framework.common.util.coroutine.shutdownCoroutineEnvironment
import plutoproject.framework.common.util.initPluginDataFolder
import plutoproject.framework.common.util.inject.modifyExistedKoinOrCreate
import plutoproject.framework.common.util.serverThread
import plutoproject.framework.velocity.FrameworkVelocityModule
import plutoproject.framework.velocity.disableFrameworkModules
import plutoproject.framework.velocity.enableFrameworkModules
import plutoproject.framework.velocity.loadFrameworkModules
import plutoproject.framework.velocity.util.plugin
import plutoproject.framework.velocity.util.server
import java.nio.file.Path
import java.util.logging.Logger
import plutoproject.framework.common.util.logger as utilLogger
import plutoproject.framework.velocity.util.server as utilServer

class PlutoVelocityPlatform {
    fun load(server: ProxyServer, logger: Logger, dataDirectoryPath: Path) {
        utilServer = server
        utilLogger = logger
        serverThread = Thread.currentThread()
        dataDirectoryPath.toFile().initPluginDataFolder()
        modifyExistedKoinOrCreate {
            modules(FrameworkCommonModule, FrameworkVelocityModule)
        }
        loadFrameworkModules()
    }

    fun enable() {
        plugin = server.pluginManager.getPlugin("plutoproject").get()
        enableFrameworkModules()
    }

    fun disable() {
        disableFrameworkModules()
        shutdownCoroutineEnvironment()
    }
}
