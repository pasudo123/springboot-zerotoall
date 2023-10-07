package com.example.springboot3reactivewithresilience4j.poc

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DemoApplicationService(
    private val aService: DemoDomainAService,
    private val bService: DemoDomainBService
) {

    companion object {
        const val DEMO_SERVICE = "demoService"
    }

    @CircuitBreaker(name = DEMO_SERVICE, fallbackMethod = "fallbackCircuit")
    fun doSomething(): Mono<DemoResources.DemoResponse> {
        return aService.doSomething()
    }

    fun fallbackCircuit(
        exception: Exception
    ): Mono<DemoResources.DemoResponse> {
        val cause = when (exception) {
            is DemoRecordException -> exception.message ?: "empty-message"
            is CallNotPermittedException -> "not permitted : ${exception.message}"
            else -> "알 수 없는 에러?!"
        }
        return bService.doSomething(cause)
    }
}
