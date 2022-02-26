package com.example.springboottestcodebasis.util

import com.example.springboottestcodebasis.util.Helper.toKoreanReadableText
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@DisplayName("Helper 는")
internal class HelperTest {

    @Test
    @DisplayName("한국시간 표기로 작성한다.")
    fun toKoreanReadableTextTest() {

        // given
        val dateTime = LocalDateTime.of(2022, 1, 10, 15, 24, 15)

        // when
        val actual = dateTime.toKoreanReadableText()

        // then
        actual shouldBe "2022년 01월 10일 15시 24분 15초"
    }

    @Test
    @DisplayName("한국시간 표기로 작성한다. : 확장함수를 스터빙한다.")
    fun toKoreanReadableTextTest2() {

        // given
        val dateTime = LocalDateTime.of(2022, 1, 10, 15, 24, 15)
        mockkObject(Helper)
        with(Helper) {
            every { dateTime.toKoreanReadableText() } returns "empty"
        }

        // when
        val actual = dateTime.toKoreanReadableText()

        // then
        actual shouldBe "empty"
    }
}