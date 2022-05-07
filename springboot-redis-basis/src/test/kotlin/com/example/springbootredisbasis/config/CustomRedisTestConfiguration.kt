package com.example.springbootredisbasis.config

import com.example.springbootredisbasis.CustomRedisContainer.Companion.REDIS_CONTAINER
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@TestConfiguration
internal class CustomRedisTestConfiguration {

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val connectionFactory = LettuceConnectionFactory(REDIS_CONTAINER.host, REDIS_CONTAINER.firstMappedPort)
            .apply {
                this.database = 0
                this.afterPropertiesSet()
            }

        return RedisTemplate<String, String>().apply {
            this.setConnectionFactory(connectionFactory)
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            this.afterPropertiesSet()
        }
    }
}