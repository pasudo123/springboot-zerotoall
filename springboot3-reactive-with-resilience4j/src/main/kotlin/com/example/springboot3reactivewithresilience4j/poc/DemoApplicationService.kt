package com.example.springboot3reactivewithresilience4j.poc

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DemoApplicationService(
    private val aService: DemoDomainAService,
    private val bService: DemoDomainBService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val DEMO_SERVICE = "demoService"
    }

    @CircuitBreaker(name = DEMO_SERVICE, fallbackMethod = "fallbackDemoService")
    fun doSomething(): Mono<DemoResources.DemoResponse> {
        log.info("circuit doSomething!!!")
        return aService.doSomething()
    }

    private fun fallbackDemoService(
        exception: Exception
    ): Mono<DemoResources.DemoResponse> {
        val cause = exception.message ?: "empty-message"
        return bService.doSomething(cause)
    }

    private fun fallbackDemoService(
        exception: CallNotPermittedException
    ): Mono<DemoResources.DemoResponse> {
        val cause = "not permitted : ${exception.message}"
        return bService.doSomething(cause)
    }
}
