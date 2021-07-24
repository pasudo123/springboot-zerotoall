package com.example.springboottestcodebasis.exercise

import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.test.context.junit.jupiter.EnabledIf

@DisplayName("커스텀한 컨디션을 가지고 테스트코드를 수행시킬 수 있다.")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Exercise04 {

    private val elements = listOf("apple", "banana")
    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("a 문자에 대해서 테스트를 수행한다.")
    @EnabledIf("conditionalElementOnList")
    fun doTestConditionalByAppleString() {
        logger.info { "apple test code exec" }
    }

    @Test
    @DisplayName("b 문자에 대해서 테스트를 수행한다.")
    @EnabledIf("conditionalElementOnList")
    fun doTestConditionalByBananaString() {
        logger.info { "banana test code exec" }
    }

    fun conditionalElementOnList(): Boolean {
        val element = elements.shuffled().first()
        logger.info { "shuffled element : [$element]" }
        return element.startsWith("a")
    }
}