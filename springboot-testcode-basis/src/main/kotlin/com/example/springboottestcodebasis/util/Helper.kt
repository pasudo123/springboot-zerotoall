package com.example.springboottestcodebasis.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Helper {

    private val KOREAN_READABLE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")

    /**
     * LocalDateTime 을 한국표기 시간으로 변경한다.
     * - ex) 2022년 2월 26일 10시 35분 12초
     */
    fun LocalDateTime.toKoreanReadableText(): String {
        return this.format(KOREAN_READABLE_FORMATTER)
    }
}