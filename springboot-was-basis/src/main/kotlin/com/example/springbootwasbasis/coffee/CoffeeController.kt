package com.example.springbootwasbasis.coffee

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("coffee")
class CoffeeController {

    @GetMapping
    fun get(): String {
        // Thread.sleep(Random.nextLong(6000, 6001))
        val randomUUID = UUID.randomUUID().toString()
        return """
            {
                "name": "coffee-$randomUUID",
                "date": "${LocalDateTime.now()}"
            }
        """.trimIndent()
    }
}
