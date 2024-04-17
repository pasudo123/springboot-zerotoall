package com.example.springbootgrpcbasis.protobuf

import com.example.proto.sample.HelloRequestKt
import com.example.proto.sample.HelloResponseKt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("simple-protobuf")
class SimpleProtobufController() {

    @PostMapping
    fun simple(
        @RequestBody request: HelloRequestKt
    ): HelloResponseKt {
        return HelloResponseKt
    }
}
