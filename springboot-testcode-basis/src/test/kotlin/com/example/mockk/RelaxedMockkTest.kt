package com.example.mockk

import com.example.TestEnvironment
import com.example.mockk.model.Car
import com.example.mockk.model.CarMaker
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

@TestEnvironment
class RelaxedMockkTest {

    @TestFactory
    @DisplayName("partial mocking 을 해본다.")
    fun simpleTest(): List<DynamicTest> {

        val carMaker: CarMaker = mockk()
        val paramNames = listOf("소방차", "경찰차")

        return listOf(
            DynamicTest.dynamicTest("모킹된 결과를 반환한다.") {
                // given
                every { carMaker.makeCars(paramNames) } returns listOf(Car("붕붕"), Car("씽씽"))

                // when
                val cars = carMaker.makeCars(paramNames)

                // then
                cars.size shouldBe 2
                cars[0].name shouldBe "붕붕"
                cars[1].name shouldBe "씽씽"
            },

            DynamicTest.dynamicTest("실제 함수를 반환한다.") {
                // given
                every { carMaker.makeCars(paramNames) } answers { callOriginal() }

                // when
                val cars = carMaker.makeCars(paramNames)

                // then
                cars.size shouldBe 2
                cars[0].name shouldBe "고급 소방차"
                cars[1].name shouldBe "고급 경찰차"
            },
        )
    }
}