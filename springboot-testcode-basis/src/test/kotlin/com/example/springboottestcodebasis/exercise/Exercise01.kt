package com.example.springboottestcodebasis.exercise

import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.random.Random

@DisplayName("TestInstance 의 속성에 따라서\n 랜덤값을 각각의 테스트는 공유하거나 공유하지 않는다.")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Exercise01 {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    @DisplayName("TestInstance.Lifecycle.PER_METHOD 는 필드를 공유하지 않는다.")
    inner class TestInstancePerMethodTest {
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

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("TestInstance.Lifecycle.PER_CLASS 는 필드를 공유한다.")
    inner class TestInstancePerClassTest {
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
}