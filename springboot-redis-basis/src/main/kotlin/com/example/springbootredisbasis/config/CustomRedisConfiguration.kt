package com.example.springbootredisbasis.config

import com.example.springbootredisbasis.domain.coffee.Coffee
import com.fasterxml.jackson.databind.ObjectMapper
import io.lettuce.core.ClientOptions
import io.lettuce.core.SocketOptions
import io.lettuce.core.TimeoutOptions
import io.lettuce.core.resource.ClientResources
import io.lettuce.core.resource.DefaultClientResources
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.util.Optional

@Configuration
class CustomRedisConfiguration(
    private val multipleRedisProps: CustomMultipleRedisProps,
    private val redisProperties: RedisProperties,
    private val objectMapper: ObjectMapper,
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun stringRedisTemplate(): StringRedisTemplate {
        val standaloneConfiguration = RedisStandaloneConfiguration(
            redisProperties.host,
            redisProperties.port
        ).apply {
            this.password = RedisPassword.none()
            this.database = 0
        }

        val connectionFactory = LettuceConnectionFactory(
            standaloneConfiguration
        ).apply {
            this.afterPropertiesSet()
        }

        return StringRedisTemplate(connectionFactory).apply {
            this.afterPropertiesSet()
        }
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {

        logger.info { "redis props size : ${multipleRedisProps.redisGroup.size}" }

        val node = multipleRedisProps.redisGroup.first()

        val standaloneConfiguration = RedisStandaloneConfiguration(
            node.host!!,
            node.port!!,
        ).apply {
            this.password = node.getRedisPassword()
            this.database = node.database!!
        }

        val connectionFactory = LettuceConnectionFactory(standaloneConfiguration)
            .apply {
                this.afterPropertiesSet()
            }

        return RedisTemplate<String, String>().apply {
            this.setConnectionFactory(connectionFactory)
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            this.afterPropertiesSet()
        }
    }

    @Bean
    fun reactiveRedisTemplate(
        reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory
    ): ReactiveRedisTemplate<String, Coffee> {

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

        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializerContext)
    }
}