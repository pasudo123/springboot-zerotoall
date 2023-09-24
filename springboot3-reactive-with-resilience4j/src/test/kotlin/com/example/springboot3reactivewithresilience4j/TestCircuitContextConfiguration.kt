package com.example.springboot3reactivewithresilience4j

import com.example.springboot3reactivewithresilience4j.circuitbreaker.CoffeeService
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestCircuitContextConfiguration {

    @Bean
    fun testCustomizer(): CircuitBreakerConfigCustomizer {
        return CircuitBreakerConfigCustomizer.of(CoffeeService.COFFEE_SERVICE) { builder ->
            builder.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            builder.slidingWindowSize(10)
            builder.minimumNumberOfCalls(10)
            builder.failureRateThreshold(70F) // ${n}% 임계치보다 같거나 클 떄, CLOSE -> OPEN 전이설정
        }
    }
}
