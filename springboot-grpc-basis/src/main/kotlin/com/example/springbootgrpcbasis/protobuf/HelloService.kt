package com.example.springbootgrpcbasis.protobuf

import com.example.proto.sample.HelloRequest
import com.example.proto.sample.HelloResponse
import com.example.proto.sample.HelloServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class HelloService : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {

    override suspend fun hello(request: HelloRequest): HelloResponse {
        return HelloResponse
            .newBuilder()
            .setMessage("Hello ${request.name}")
            .build()
    }
}