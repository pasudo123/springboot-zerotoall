package com.example.springboottestcodebasis.exercise

import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import kotlin.random.Random

/**
 * 명시적으로 테스트 인스턴스의 라이프 사이클을 property 에서 변경할 수 있다.
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-instance-lifecycle-changing-default
 *
 * @TestPropertySource 를 통해서 상속이 안된다. 흠...
 */
@DisplayName("properties 혹은 yml 또는 시스템 설정으로 테스트 인스턴스 라이프사이클을 변경한다.")
@TestPropertySource(
    locations = ["classpath:junit-platform.properties"],
    properties = ["junit.jupiter.testinstance.lifecycle.default=per_class"]
)
class Exercise02 {

    private val logger = KotlinLogging.logger {}
    private val randomNumber = Random.nextLong(1000000)

    @Test
    fun randomNumberCheckTest01() {
        logger.info { "[sample-test-01] : $randomNumber" }
    }

    @Test
    fun randomNumberCheckTest02() {
        logger.info { "[sample-test-02] : $randomNumber" }
    }
}