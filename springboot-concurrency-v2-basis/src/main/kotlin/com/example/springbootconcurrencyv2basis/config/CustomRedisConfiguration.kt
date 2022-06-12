package com.example.springbootconcurrencyv2basis.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class CustomRedisConfiguration(
    private val redisProperties: RedisProperties
) {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun bagRedisTemplate(): RedisTemplate<String, String> {
        val connectionFactory = redisConnectionFactory().apply {
            this.database = 0
            this.afterPropertiesSet()
        }

        return RedisTemplate<String, String>().apply {
            setConnectionFactory(connectionFactory)
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
        }
    }

}