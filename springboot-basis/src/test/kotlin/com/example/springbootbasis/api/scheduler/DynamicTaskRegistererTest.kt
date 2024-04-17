package com.example.springbootbasis.api.scheduler

import org.junit.jupiter.api.Test
import java.util.TimeZone

class DynamicTaskRegistererTest {

    @Test
    fun `아시아 서울 타임존 세팅`() {
        val timezone = TimeZone.getTimeZone("Asia/Seoul")
        println(timezone.id)
        println(timezone.displayName)
        println(timezone.dstSavings)
        println(timezone.rawOffset)
    }
}
