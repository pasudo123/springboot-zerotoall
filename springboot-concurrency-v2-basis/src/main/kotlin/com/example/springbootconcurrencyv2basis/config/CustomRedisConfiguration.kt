package com.example.springbootconcurrencyv2basis.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class CustomRedisConfiguration(
    private val redisProperties: RedisProperties
) {

    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun bagRedisTemplate(): StringRedisTemplate {
        val connectionFactory = redisConnectionFactory().apply {
            this.database = 0
            this.afterPropertiesSet()
        }

        return StringRedisTemplate(connectionFactory)
    }

}