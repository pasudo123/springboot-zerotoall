package com.example.springboottestcodebasis.mockk.capture

import com.example.TestEnvironment
import com.example.springboottestcodebasis.mockk.Coffee
import com.example.springboottestcodebasis.mockk.CoffeeGetService
import com.example.springboottestcodebasis.mockk.CoffeeRepository
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@TestEnvironment
class CapturingTest {

    private val coffeeRepository: CoffeeRepository = mockk()
    private val coffeeGetService = CoffeeGetService(coffeeRepository)

    @Test
    @DisplayName("금액을 건네주고, 금액에 맞는 커피종류를 반환한다.")
    fun capturingTest() {

        // given
        val price = slot<Long>()

        every { coffeeRepository.findAllByPrice(capture(price)) } answers {
            when (price.captured) {
                3000L -> {
                    listOf(Coffee("아메리카노", 3000L))
                }
                5000L -> {
                    listOf(Coffee("카페라떼", 5000L))
                }
                8000L -> {
                    listOf(Coffee("콜드블루", 8000L))
                }
                else -> {
                    emptyList()
                }
            }
        }

        // when
        val actual = coffeeGetService.getCoffeesByPrices(listOf(3000L, 5000L, 9000L))

        // then
        actual.asClue { coffee ->
            coffee.size shouldBe 2
            coffee.minByOrNull { it.price }!!.name shouldBe "아메리카노"
            coffee.maxByOrNull { it.price }!!.name shouldBe "카페라떼"
        }
    }
}