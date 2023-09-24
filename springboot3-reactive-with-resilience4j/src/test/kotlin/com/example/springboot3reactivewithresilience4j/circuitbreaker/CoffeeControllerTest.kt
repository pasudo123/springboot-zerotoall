package com.example.springboot3reactivewithresilience4j.circuitbreaker

import com.example.springboot3reactivewithresilience4j.Springboot3ReactiveWithResilience4jApplication
import com.example.springboot3reactivewithresilience4j.TestCircuitContextConfiguration
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.client.getForEntity

@ExtendWith(SpringExtension::class)
@AutoConfigureWebTestClient
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [Springboot3ReactiveWithResilience4jApplication::class]
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ContextConfiguration(classes = [TestCircuitContextConfiguration::class])
class CoffeeControllerTest(
    val circuitBreakerRegistry: CircuitBreakerRegistry,
    val restTemplate: TestRestTemplate,
    val webClient: WebTestClient
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private lateinit var circuitBreaker: CircuitBreaker

    @BeforeEach
    fun init() {
        // test 용 circuit override 설정은 TestCircuitContextConfiguration.kt 참고
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(CoffeeService.COFFEE_SERVICE)
        circuitBreaker.reset()
        circuitBreaker.transitionToClosedState()
    }

    @Test
    fun `circuit 이 CLOSE 상태로 유지된다`() {
        repeat(4) { restTemplate.okApiCall() }
        repeat(6) { restTemplate.recordErrorApiCall() }

        circuitBreaker.state shouldBe CircuitBreaker.State.CLOSED
    }

    @Test
    fun `circuit 이 OPEN 상태로 전이된다`() {
        repeat(3) { restTemplate.okApiCall() }
        repeat(7) { restTemplate.recordErrorApiCall() }

        circuitBreaker.state shouldBe CircuitBreaker.State.OPEN
    }

    @Test
    fun `circuit 은 ignore Exception 에 대해서 상태전이가 안된다`() {
        repeat(100) { restTemplate.ignoreErrorApiCall() }

        circuitBreaker.state shouldBe CircuitBreaker.State.CLOSED
    }

    private fun TestRestTemplate.recordErrorApiCall() {
        restTemplate.getForEntity<String>("/coffee/error-record", String::class)
    }

    private fun TestRestTemplate.ignoreErrorApiCall() {
        restTemplate.getForEntity<String>("/coffee/error-ignore", String::class)
    }

    private fun TestRestTemplate.okApiCall() {
        restTemplate.getForEntity<String>("/coffee/ok", String::class)
    }
}
