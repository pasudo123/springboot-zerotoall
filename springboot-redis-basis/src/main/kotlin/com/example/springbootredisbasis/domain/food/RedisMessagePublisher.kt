package com.example.springbootredisbasis.domain.food

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component

@Component
class RedisMessagePublisher(
    private val stringRedisTemplate: StringRedisTemplate,
) : MessagePublisher {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun sendMessage(message: String, topic: ChannelTopic) {
        try {
            stringRedisTemplate.convertAndSend(topic.topic, message)
            log.info("publish to topic[${topic.topic}] : $message")
        } catch (exception: Exception) {
            log.error("redis pub error : ${exception.message}")
        }
    }
}