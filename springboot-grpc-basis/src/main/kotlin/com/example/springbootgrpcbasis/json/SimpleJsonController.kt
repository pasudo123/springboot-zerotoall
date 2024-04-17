package com.example.springbootgrpcbasis.json

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("simple-json")
class SimpleJsonController {

    @GetMapping
    fun simple(): SimpleDummy {
        return SimpleDummy("hello-${UUID.randomUUID()}")
    }
}

data class SimpleDummy(
    val message: String
)
