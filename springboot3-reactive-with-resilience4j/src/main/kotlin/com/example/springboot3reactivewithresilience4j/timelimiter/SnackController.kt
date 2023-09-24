package com.example.springboot3reactivewithresilience4j.timelimiter

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("snack")
class SnackController(
    private val snackService: SnackService
) {

    @GetMapping("ok")
    fun ok(): Mono<SnackDto> {
        return snackService.ok()
    }

    @GetMapping("timeout")
    fun timeout(
        @RequestParam second: Long
    ): Mono<SnackDto> {
        return snackService.timeout(second)
    }

    @GetMapping("error")
    fun error(): Mono<SnackDto> {
        return snackService.error()
    }
}
