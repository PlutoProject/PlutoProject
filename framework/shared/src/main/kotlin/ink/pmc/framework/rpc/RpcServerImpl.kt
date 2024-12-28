package ink.pmc.framework.rpc

import io.grpc.Grpc
import io.grpc.InsecureServerCredentials
import io.grpc.Server
import io.grpc.ServerBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import plutoproject.framework.common.api.rpc.RpcServer
import plutoproject.framework.common.config.RpcConfig
import plutoproject.framework.common.rpc.InternalErrorInterceptor
import java.util.logging.Level

