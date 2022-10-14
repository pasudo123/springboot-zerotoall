package com.example.springbootreactivebasis.domain.person

import com.example.springbootreactivebasis.config.toJson
import com.example.springbootreactivebasis.config.toObject
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.StringRedisConnection
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class PersonRepository(
    private val reactiveStringRedisTemplate: ReactiveStringRedisTemplate,
    private val stringRedisTemplate: StringRedisTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun save(person: Person) {
        val redisKey = person.getKey()
        log.info("\n${person.toJson()}")
        reactiveStringRedisTemplate.opsForValue().setIfAbsent(redisKey, person.toJson()).awaitSingleOrNull()
        reactiveStringRedisTemplate.expire(redisKey, Duration.ofSeconds(TTL_SECONDS)).awaitSingleOrNull()
    }

    fun saveAll(persons: List<Person>) {
        stringRedisTemplate.execute { conn ->
            val stringConn = conn as StringRedisConnection

            persons.forEach { person ->
                val redisKey = person.getKey()
                stringConn.set(redisKey, person.toJson())
                stringConn.expire(redisKey, TTL_SECONDS)
            }

            return@execute
        }
    }

    /**
     * 안됨
     */
//    suspend fun saveAll(persons: List<Person>) {
//        val personFluxGroup = persons.toFlux()
//
//        reactiveStringRedisTemplate.execute { conn ->
//            personFluxGroup.map { person ->
//                val redisKey = "$KEY:${person.id}"
//                val stringCommands = conn.stringCommands()
//
//                stringCommands.setEX(
//                    ByteBuffer.wrap(redisKey.toByteArray()),
//                    ByteBuffer.wrap(person.toJson().toByteArray()),
//                    Expiration.seconds(TTL_SECONDS)
//                ).subscribe()
//            }
//            return@execute personFluxGroup
//        }.subscribe()
//    }

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

    private fun Person.getKey(): String {
        return "$KEY:${this.id}"
    }

    companion object {
        private const val KEY = "person"
        private const val KEY_PATTERN = "$KEY:*"
        private const val TTL_SECONDS = 300L
        private const val COUNT = 1000000000000L
    }
}
