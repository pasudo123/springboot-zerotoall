package com.example.springbootredisbasis

import com.example.springbootredisbasis.CustomRedisContainer.Companion.REDIS_CONTAINER
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@TestConfiguration
@DependsOn("CustomRedisContainer")
class IntergrationTestConfiguration {

    @Bean
    fun redisConnectionFactory(
    ): LettuceConnectionFactory {
        return LettuceConnectionFactory(REDIS_CONTAINER.host, REDIS_CONTAINER.firstMappedPort)
            .apply {
                this.database = 0
            }
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            this.setConnectionFactory(redisConnectionFactory())
        }
    }
}