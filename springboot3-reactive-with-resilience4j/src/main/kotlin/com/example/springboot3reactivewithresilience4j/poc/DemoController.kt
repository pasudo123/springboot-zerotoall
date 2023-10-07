package com.example.springboot3reactivewithresilience4j.poc

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalTime

@RestController
@RequestMapping("demo")
class DemoController(
    private val demoApplicationService: DemoApplicationService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun get(
        @RequestParam result: Boolean
    ): Mono<DemoResources.DemoResponse> {
        return demoApplicationService.doSomething(result)
    }

    @GetMapping("dummy")
    fun getDummy(): Mono<DemoResources.DemoResponse> {
        log.info("Hello World with dummy")
        val response = DemoResources.DemoResponse(
            text = "Hello World",
            time = LocalTime.now()
        )

        return Mono.just(response)
    }
}
