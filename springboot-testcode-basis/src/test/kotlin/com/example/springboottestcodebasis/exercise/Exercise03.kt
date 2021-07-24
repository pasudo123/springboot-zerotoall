package com.example.springboottestcodebasis.exercise

import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import kotlin.random.Random

@DisplayName("MethodOrder 를 통해서 순서대로 테스트코드를 수행할 수 있다.")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class Exercise03 {

    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("bananaTest 01")
    @Order(10)
    fun bananaTest01() {
        logger.info { "current test : ${Random.nextLong(1, 99999)}" }
    }

    @Test
    @DisplayName("bananaTest 02")
    @Order(20)
    fun bananaTest02() {
        logger.info { "current test : ${Random.nextLong(1, 99999)}" }
    }

    @Test
    @DisplayName("appleTest 01")
    @Order(30)
    fun appleTest01() {
        logger.info { "current test : ${Random.nextLong(1, 99999)}" }
    }
}