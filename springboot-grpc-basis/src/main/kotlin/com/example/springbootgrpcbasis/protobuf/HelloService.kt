package com.example.springbootgrpcbasis.protobuf

import com.example.proto.sample.HelloRequest
import com.example.proto.sample.HelloResponse
import com.example.proto.sample.HelloServiceGrpcKt

class HelloService : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun hello(request: HelloRequest): HelloResponse {

    }
}