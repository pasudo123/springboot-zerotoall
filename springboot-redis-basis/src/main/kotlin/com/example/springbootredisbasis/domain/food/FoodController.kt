package com.example.springbootredisbasis.domain.food

import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@RequestMapping("foods")
class FoodController(
    private val redisMessagePublisher: RedisMessagePublisher,
    private val topic: ChannelTopic
) {

    @GetMapping
    fun sendMessage() {
        val message = "hello-${LocalTime.now()}"
        redisMessagePublisher.sendMessage(message, topic)
    }
}