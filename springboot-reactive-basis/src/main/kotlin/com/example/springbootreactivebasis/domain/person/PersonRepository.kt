package com.example.springbootreactivebasis.domain.person

import com.example.springbootreactivebasis.config.toJson
import com.example.springbootreactivebasis.config.toObject
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class PersonRepository(
    private val reactiveStringRedisTemplate: ReactiveStringRedisTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun save(person: Person) {
        val redisKey = "$KEY:${person.id}"
        log.info("\n${person.toJson()}")
        reactiveStringRedisTemplate.opsForValue().setIfAbsent(redisKey, person.toJson()).awaitSingleOrNull()
        reactiveStringRedisTemplate.expire(redisKey, Duration.ofSeconds(180)).awaitSingleOrNull()
    }

    suspend fun findAll(): List<Person> {
        val keys = mutableListOf<String>()

        val scanResult = reactiveStringRedisTemplate.scan(scanOptions).all { key -> keys.add(key) }.awaitSingleOrNull()
        if (scanResult == null || keys.isEmpty()) {
            return emptyList()
        }

        return reactiveStringRedisTemplate.opsForValue().multiGet(keys).awaitSingleOrNull()?.map { json ->
            json.toObject()
        } ?: emptyList()
    }

    fun findAllWithBlock(): List<Person> {
        val keys = mutableListOf<String>()

        val scanResult = reactiveStringRedisTemplate.scan(scanOptions).all { key -> keys.add(key) }.block()
        if (scanResult == null || keys.isEmpty()) {
            return emptyList()
        }

        return reactiveStringRedisTemplate.opsForValue().multiGet(keys).block()?.map { json ->
            json.toObject()
        } ?: emptyList()
    }

    private val scanOptions = ScanOptions.ScanOptionsBuilder().apply {
        this.count(COUNT)
        this.match(KEY_PATTERN)
    }.build()

    companion object {
        private const val KEY = "person"
        private const val KEY_PATTERN = "$KEY:*"
        private const val COUNT = 1000000000000L
    }
}