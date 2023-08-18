package com.example.springbootredisbasis.domain.food

import org.springframework.data.redis.listener.ChannelTopic

interface MessagePublisher {

    fun sendMessage(message: String, topic: ChannelTopic)
}
