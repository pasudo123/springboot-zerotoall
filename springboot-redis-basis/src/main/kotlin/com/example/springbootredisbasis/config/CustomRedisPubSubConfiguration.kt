package com.example.springbootredisbasis.config

import com.example.springbootredisbasis.domain.food.RedisMessageSubscriber
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@Configuration
class CustomRedisPubSubConfiguration(
    private val redisProperties: RedisProperties
) {

    companion object {
        const val TOPIC = "sample_topic"
    }

    @Bean
    fun channelTopic(): ChannelTopic {
        return ChannelTopic(TOPIC)
    }

    @Bean
    fun messageListenerAdapter(
        redisMessageSubscriber: RedisMessageSubscriber
    ): MessageListenerAdapter {
        return MessageListenerAdapter(redisMessageSubscriber)
    }

    @Bean
    fun redisMessageListenerContainer(
        messageListenerAdapter: MessageListenerAdapter,
        topic: ChannelTopic
    ): RedisMessageListenerContainer {

        val standaloneConfiguration = RedisStandaloneConfiguration(
            redisProperties.host,
            redisProperties.port
        ).apply {
            this.password = RedisPassword.none()
            this.database = 0
        }

        val connectionFactory = LettuceConnectionFactory(standaloneConfiguration).apply {
            this.afterPropertiesSet()
        }

        return RedisMessageListenerContainer().apply {
            this.setConnectionFactory(connectionFactory)
            this.addMessageListener(messageListenerAdapter, topic)
        }
    }
}