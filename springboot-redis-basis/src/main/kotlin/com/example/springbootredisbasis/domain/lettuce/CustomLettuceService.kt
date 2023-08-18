package com.example.springbootredisbasis.domain.lettuce

import io.lettuce.core.RedisClient
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class CustomLettuceService(
    private val redisClient: RedisClient
) {

    fun doSomething() {
        val connection = redisClient.connect()
        val command = connection.sync()
        command.set("sample", "sample")
        command.expire("sample", Duration.ofSeconds(30))
//        connection.close()
    }
}
