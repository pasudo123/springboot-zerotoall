package com.example.springbootcloudclientbasis.index

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RefreshScope
@RestController
@RequestMapping("index")
class IndexController {

    @Value("\${message: Hello default}")
    private lateinit var message: String

    @GetMapping
    fun getMessage(): String {
        return this.message
    }
}