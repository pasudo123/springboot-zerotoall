package com.example.springboot3reactivewithresilience4j.poc

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("demo")
class DemoController(
    private val demoApplicationService: DemoApplicationService
) {

    @GetMapping
    fun getSample(
        @RequestParam result: Boolean
    ): Mono<DemoResources.DemoResponse> {
        return demoApplicationService.doSomething(result)
    }
}
