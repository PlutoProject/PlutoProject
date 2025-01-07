package plutoproject.framework.common.api.rpc

import io.grpc.Server
import io.grpc.ServerBuilder
import plutoproject.framework.common.util.inject.Koin

interface RpcServer {
    companion object : RpcServer by Koin.get()

    val server: Server

    fun apply(block: ServerBuilder<*>.() -> Unit)

    fun start()

    fun stop()
}
