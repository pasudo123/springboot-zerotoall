package com.example.mockk

import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.random.Random

@DisplayName("Mockk 을 가지고 verify 로 메소드 호출여부를 증명한다.")
class MockkVerifyTest {

    @Test
    @DisplayName("verify 를 통해 몇번 호출되었는지 셀 수 확인할 수 있다.")
    fun verifyExactTest() {

        // given : relaxed 를 통해 별도의 answer 는 붙이지 않아도 된다.
        val process: Process = mockk(relaxed = true)
        val sequence = 5

        // when
        repeat(sequence) {
            process.call(Random.nextInt(0, 9))
        }

        // then
        // 검증
        verify(exactly = 5) {
            process.call(any())
        }
        // 검증된 내용을 기반 호출이 제대로 되었는지 확인
        confirmVerified(process)
    }

    @Test
    @DisplayName("returnsMany 를 통해 순서에 맞에 어떠한 값들이 나왔는지 설정할 수 있다.")
    fun test() {

        // given
        val process: Process = mockk()
        every { process.call(any()) } returnsMany listOf(
            "ok-9", "ok-10", "ok-11"
        )

        // when
        val elements = mutableListOf<String>()
        repeat(5) {
            elements.add(process.call(it))
        }

        // then
        elements.size shouldBe 5
        elements[0] = "ok-9"
        elements[1] = "ok-10"
        elements[2] = "ok-11" // 해당 메소드가 더 실행되면, 추가적인 리턴값은 마지막 요소로 된다.
        elements[3] = "ok-11"
        elements[4] = "ok-11"
        verify { process.call(any()) }
    }
}

class Process {

    fun call(number: Int): String {
        return "ok-$number"
    }
}