package plutoproject.framework.paper.util.hook

import plutoproject.framework.paper.util.server

var vaultHook: VaultHook? = null
var sparkHook: SparkHook? = null

fun initHooks() {
    if (server.pluginManager.getPlugin("Vault") != null) {
        vaultHook = VaultHook()
    }
    if (server.pluginManager.getPlugin("spark") != null) {
        sparkHook = SparkHook()
    }
}
