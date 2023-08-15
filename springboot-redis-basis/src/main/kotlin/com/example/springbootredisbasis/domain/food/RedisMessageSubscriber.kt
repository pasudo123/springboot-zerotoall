package com.example.springbootredisbasis.domain.food

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RedisMessageSubscriber(
    private val stringRedisTemplate: StringRedisTemplate
) : MessageListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val body = message.body
        val channel = message.channel
        log.info("sub from channel[${String(channel)}] : ${String(body)}")
    }

    // 기본저긍로 reconnect 를 수행하고 있음
//    @Scheduled(cron = "0 */1 * * * *", zone = "Asia/Seoul")
//    fun schedulePing() {
//        stringRedisTemplate.execute { conn ->
//            conn.ping()
//            log.info("redis ping")
//        }
//    }
}