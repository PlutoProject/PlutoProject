package ink.pmc.framework.playerdb

import ink.pmc.framework.playerdb.proto.PlayerDbRpc
import plutoproject.framework.common.playerdb.DatabaseNotifier
import java.util.*

class ProxyDatabaseNotifier : DatabaseNotifier {
    override suspend fun notify(id: UUID) {
        PlayerDbRpc.notifyDatabaseUpdate(id)
    }
}