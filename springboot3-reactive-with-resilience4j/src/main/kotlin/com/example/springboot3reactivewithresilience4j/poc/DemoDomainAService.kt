package com.example.springboot3reactivewithresilience4j.poc

import com.example.springboot3reactivewithresilience4j.Metrics
import com.example.springboot3reactivewithresilience4j.poc.DemoApplicationService.Companion.DEMO_SERVICE
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalTime
import kotlin.random.Random

@Service
class DemoDomainAService(
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    private val log = LoggerFactory.getLogger(javaClass)
    val circuitBreaker = circuitBreakerRegistry.circuitBreaker(DEMO_SERVICE)

    @Throws(DemoRecordException::class)
    fun doSomething(result: Boolean): Mono<DemoResources.DemoResponse> {
        if (result) {
            log.info("AAA doSomething success, result=$result")
            val response = DemoResources.DemoResponse(
                text = "A Service OK!",
                time = LocalTime.now(),
                metrics = Metrics.of(circuitBreaker)
            )
            return Mono.just(response)
        }

        log.info("AAA doSomething failed, result=$result")
        return Mono.error(DemoRecordException("A Service 에서 에러 발생, time=${LocalTime.now()}"))
    }
}
