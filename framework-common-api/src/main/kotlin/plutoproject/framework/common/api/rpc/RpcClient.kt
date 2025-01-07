package plutoproject.framework.common.api.rpc

import io.grpc.Channel
import plutoproject.framework.common.util.inject.Koin

interface RpcClient {
    companion object : RpcClient by Koin.get()

    val channel: Channel

    fun start()

    fun stop()
}
