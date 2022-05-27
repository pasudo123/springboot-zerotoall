package com.example.springbootbasis.task

import com.example.springbootbasis.exception.AppleException
import com.example.springbootbasis.exception.BananaException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class MyTask {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(initialDelay = 2000, fixedDelay = 3000, zone = "Asia/Seoul")
    fun doSchedule() {
        // 1 ~ 5

        when (val number = Random.nextLong(1, 7)) {
            1L, 2L -> throw AppleException("사과 익셉션 발생 : $number")
            3L, 4L -> throw BananaException("바나나 익셉션 발생 : $number")
            5L -> throw RuntimeException("런타입 익셉션 발생 : $number")
            else -> log.info("별 탈 없이 수행됨 : $number")
        }
    }
}