package com.example.springbootreactivebasis.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate

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
//        val stringSerializer = StringRedisSerializer()

//         val jsonRedisSerializer = Jackson2JsonRedisSerializer(String::class.java)
//        val redisSerializerContext = RedisSerializationContext
//            .newSerializationContext<String, String>()
//            .key(stringSerializer)
//            .hashKey(stringSerializer)
//            .value(stringSerializer)
//            .hashValue(stringSerializer)
//            .build()

        return ReactiveStringRedisTemplate(reactiveRedisConnectionFactory)
    }
}