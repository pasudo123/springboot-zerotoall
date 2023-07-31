package com.example.springbootconcurrencyv2basis.readwrite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("redis-read-write")
class RedisReadWriteController(
    private val redisReadWriteRepository: RedisReadWriteRepository
) {

    @GetMapping("twice")
    fun twice() = runBlocking(Dispatchers.IO) {

        val uuid = UUID.randomUUID().toString()

        redisReadWriteRepository.save(uuid)

        delay(1000L)

        listOf(
            async(Dispatchers.IO) {redisReadWriteRepository.save(uuid, false) },
            async(Dispatchers.IO) { redisReadWriteRepository.unlink(uuid) }
        ).awaitAll()
    }
}