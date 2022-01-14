package com.example.mockk

import com.example.TestEnvironment
import com.example.mockk.service.Car
import com.example.mockk.service.CarMaker
import com.example.mockk.service.OrderClient
import com.example.mockk.service.OrderService
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

@TestEnvironment
class MockkProdTest {

    @TestFactory
    @DisplayName("partial mocking 을 해본다.")
    fun partialMockingTest(): List<DynamicTest> {

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

    @TestFactory
    @DisplayName("relaxed 설정과 미설정에 따른 결과차이를 본다.")
    fun relaxedMockkTest(): List<DynamicTest> {

        return listOf(
            DynamicTest.dynamicTest("orderClient = mockk(relaxed = true)") {

                // given
                val orderClient: OrderClient = mockk(relaxed = true)
                val orderService = OrderService(orderClient)

                // when
                val result = orderService.orderWithPayAmount()

                // then : string 의 경우에 empty 가 반환된다.
                verify { orderClient.order() }
                result shouldBe ""
            },
            DynamicTest.dynamicTest("orderClient = mockk() -> every {}") {

                // given
                val orderClient: OrderClient = mockk()
                val orderService = OrderService(orderClient)
                every { orderClient.order() } answers { "mocking ordered" }

                // when
                val result = orderService.orderWithPayAmount()

                // then
                verify { orderClient.order() }
                result shouldBe "mocking ordered"
            },
            DynamicTest.dynamicTest("orderClient = mockk() -> callOriginal()") {

                // given
                val orderClient: OrderClient = mockk()
                val orderService = OrderService(orderClient)
                every { orderClient.order() } answers { callOriginal() }

                // when
                val result = orderService.orderWithPayAmount()

                // then
                verify { orderClient.order() }
                result shouldBe "ordered"
            }
        )
    }
}