package com.example.springbootredisbasis.config

import com.example.springbootredisbasis.config.domain.coffee.Coffee
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class CustomRedisConfiguration(
    private val redisProperties: RedisProperties,
    private val objectMapper: ObjectMapper
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val connectionFactory = LettuceConnectionFactory(redisProperties.host, redisProperties.port)
            .apply {
                this.database = SAMPLE_DATABASE
                this.afterPropertiesSet()
            }

        return RedisTemplate<String, Any>().apply {
            this.setConnectionFactory(connectionFactory)
            this.afterPropertiesSet()
        }
    }

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisTemplate<String, Coffee> {
        val reactiveConnectionFactory = LettuceConnectionFactory(redisProperties.host, redisProperties.port)
            .apply {
                this.database = SAMPLE_REACTIVE_DARABASE
                this.afterPropertiesSet()
            }


        val keySerializer = StringRedisSerializer()
        val serializer: Jackson2JsonRedisSerializer<Coffee> = Jackson2JsonRedisSerializer(Coffee::class.java).apply {
            this.setObjectMapper(objectMapper)
        }

        val serializerContext = RedisSerializationContext.newSerializationContext<String, Coffee>(serializer).apply {
            this.key(keySerializer)
            this.hashKey(keySerializer)
            this.value(serializer)
            this.hashValue(serializer)
        }.build()

        return ReactiveRedisTemplate(reactiveConnectionFactory, serializerContext)
    }

    companion object {
        private const val SAMPLE_DATABASE = 0
        private const val SAMPLE_REACTIVE_DARABASE = 1
    }
}