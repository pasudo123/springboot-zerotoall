package com.example.springbootredisbasis.domain.food

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class RedisMessageSubscriber : MessageListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val body = message.body
        val channel = message.channel
        log.info("sub from channel[${String(channel)}] : ${String(body)}")
    }
}