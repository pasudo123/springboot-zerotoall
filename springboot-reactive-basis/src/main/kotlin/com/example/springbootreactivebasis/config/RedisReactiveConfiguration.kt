package com.example.springbootreactivebasis.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RedisReactiveConfiguration(
    private val redisProperties: RedisProperties
) {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        val standaloneConfiguration = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port).apply {
            this.password = RedisPassword.none()
            this.database = 0
        }

        return LettuceConnectionFactory(standaloneConfiguration)
    }

    @Bean
    fun reactiveStringRedisTemplate(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveStringRedisTemplate {
        return ReactiveStringRedisTemplate(reactiveRedisConnectionFactory)
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val standaloneConfiguration = RedisStandaloneConfiguration(redisProperties.host, redisProperties.port).apply {
            this.password = RedisPassword.none()
            this.database = 0
        }

        return LettuceConnectionFactory(standaloneConfiguration)
    }

    @Bean
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(redisConnectionFactory)
    }
}
