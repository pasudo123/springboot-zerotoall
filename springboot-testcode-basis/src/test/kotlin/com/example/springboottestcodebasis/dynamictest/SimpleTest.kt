package com.example.springboottestcodebasis.dynamictest

import com.example.TestEnvironment
import io.kotest.matchers.shouldBe
import mu.KLoggable
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestEnvironment
class SimpleTest {

    private var uuid: String = ""
    companion object : Any(), KLoggable {
        override val logger = logger()
    }

    @BeforeAll
    fun beforeAll() {
        logger.info { "@BeforeAll" }
    }

    @BeforeEach
    fun init() {
        logger.info { "  @BeforeEach" }
    }

    @TestFactory
    @DisplayName("동적 테스트")
    fun dynamicTestWithCollection(): List<DynamicTest> {

        /**
         * 15:45:46.345 [main] INFO com.example.springboottestcodebasis.dynamictest.SimpleTest -   @BeforeEach
         * 15:45:46.347 [main] INFO com.example.springboottestcodebasis.dynamictest.SimpleTest - [dynamicTestWithCollection]
         * 15:45:46.379 [main] INFO com.example.springboottestcodebasis.dynamictest.SimpleTest - [1] 곱셈을 한다.
         * 15:45:46.381 [main] INFO com.example.springboottestcodebasis.dynamictest.SimpleTest - [2] 곱셈을 한다.
         * 15:45:46.382 [main] INFO com.example.springboottestcodebasis.dynamictest.SimpleTest - [3] 곱셈을 한다.
         * 15:45:46.383 [main] INFO com.example.springboottestcodebasis.dynamictest.SimpleTest -   @AfterEach
         */

        logger.info { "[dynamicTestWithCollection]" }

        return listOf(
            DynamicTest.dynamicTest("[1] 곱셈을 한다.") { logger.info {"[1] 곱셈을 한다."} },
            DynamicTest.dynamicTest("[2] 곱셈을 한다.") { logger.info {"[2] 곱셈을 한다."} },
            DynamicTest.dynamicTest("[3] 곱셈을 한다.") { logger.info {"[3] 곱셈을 한다."} },
        )
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3])
    @DisplayName("파라미터라이즈 테스트")
    fun parameterizedTest(mul: Int) {

        logger.info { "[parameterizedTest]" }

        5 * mul shouldBe 5 * mul
    }

    @AfterEach
    fun afterEach() {
        logger.info { "  @AfterEach" }
    }

    @AfterAll
    fun afterAll() {
        logger.info { "@AfterAll" }
    }
}