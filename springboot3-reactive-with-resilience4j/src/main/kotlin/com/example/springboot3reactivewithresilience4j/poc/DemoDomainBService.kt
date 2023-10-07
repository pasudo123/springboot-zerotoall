package com.example.springboot3reactivewithresilience4j.poc

import com.example.springboot3reactivewithresilience4j.Metrics
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalTime
import kotlin.random.Random

@Service
class DemoDomainBService(
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    private val log = LoggerFactory.getLogger(javaClass)
    val circuitBreaker = circuitBreakerRegistry.circuitBreaker(DemoApplicationService.DEMO_SERVICE)

    @Throws(DemoRecordException::class)
    fun doSomething(
        cause: String
    ): Mono<DemoResources.DemoResponse> {
        val result = Random.nextBoolean()
        log.info("BBB doSomething, result=$result")
        if (result) {
            val response = DemoResources.DemoResponse(
                text = "B Service OK!",
                time = LocalTime.now(),
                metrics = Metrics.of(circuitBreaker),
                cause = cause
            )
            return Mono.just(response)
        }

        return Mono.error(DemoRecordException("B Service 에서 에러 발생, time=${LocalTime.now()}"))
    }
}
