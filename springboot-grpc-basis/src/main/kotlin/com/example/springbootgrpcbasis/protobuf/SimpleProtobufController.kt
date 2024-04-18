package com.example.springbootgrpcbasis.protobuf

import com.example.proto.sample.HelloResponseKt
import io.grpc.ManagedChannelBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("simple-protobuf")
class SimpleProtobufController {

    private val channel = ManagedChannelBuilder.forAddress("localhost", 8081)
        .usePlaintext()
        .build()

    @PostMapping
    fun helloToServer(): HelloResponseKt {

        return HelloResponseKt
    }

    @GetMapping
    fun hello() {

    }
}
