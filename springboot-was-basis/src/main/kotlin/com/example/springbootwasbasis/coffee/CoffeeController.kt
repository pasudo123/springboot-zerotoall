package com.example.springbootwasbasis.coffee

import com.example.springbootwasbasis.config.annotation.PathTracker
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("coffees")
class CoffeeController {

    @GetMapping
    @PathTracker
    fun findRandomOne(): Coffee {
        val randomUUID = UUID.randomUUID().toString()
        return Coffee(
            id = randomUUID,
            name = "coffee-$randomUUID",
            date = LocalDateTime.now()
        )
    }

    @GetMapping("{coffeeId}")
    @PathTracker
    fun findById(
        @PathVariable coffeeId: Long
    ): Coffee {
        return Coffee(
            id = "$coffeeId",
            name = "coffee-$coffeeId",
            date = LocalDateTime.now()
        )
    }

    @GetMapping("{coffeeId}/managers/{managerId}")
    @PathTracker
    fun findManagerByCoffeeId(
        @PathVariable coffeeId: Long,
        @PathVariable managerId: Long
    ): Manager {
        return Manager(
            managerId,
            name = "홍길동-$managerId"
        )
    }
}
