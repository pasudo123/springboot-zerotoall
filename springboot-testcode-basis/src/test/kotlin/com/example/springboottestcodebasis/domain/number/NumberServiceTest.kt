package com.example.springboottestcodebasis.domain.number

import com.example.TestEnvironment
import io.mockk.called
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestEnvironment
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class NumberServiceTest {

    private val numberOddService: NumberOddService = mockk(relaxUnitFun = true)
    private val numberEvenService: NumberEvenService = mockk(relaxUnitFun = true)
    private val numberService = NumberService(numberEvenService, numberOddService)

    @Test
    @DisplayName("number 가 홀수라서, NumberOddService 가 호출된다.")
    fun oddServiceCallTest() {

        // given
        val number = 3L

        // when
        numberService.process(number)

        // then
        verify {
            numberOddService.doSomething(any())
            numberEvenService wasNot called
        }
        verify(exactly = 0) {
            numberEvenService.doSomething(any())
        }
    }

    @Test
    @DisplayName("number 가 짝수라서, numberEvenService 가 호출된다.")
    fun evenServiceCallTest() {

        // given
        val number = 2L

        // when
        numberService.process(number)

        // then
        verify {
            numberEvenService.doSomething(any())
            numberOddService wasNot called
        }
        verify(exactly = 0) {
            numberOddService.doSomething(any())
        }
    }
}