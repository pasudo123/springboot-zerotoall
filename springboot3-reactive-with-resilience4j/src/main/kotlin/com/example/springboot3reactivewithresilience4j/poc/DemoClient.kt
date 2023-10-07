package com.example.springboot3reactivewithresilience4j.poc

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DemoClient(
    private val demoWebClient: WebClient
) {

    fun apiCall(): Mono<DemoResources.DemoResponse> {
        return demoWebClient
            .get()
            .uri("/demo/dummy")
            .retrieve()
            .bodyToMono(DemoResources.DemoResponse::class.java)
    }
}
