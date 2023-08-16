package com.example.springbootredisbasis.config

import com.example.springbootredisbasis.domain.coffee.Coffee
import com.fasterxml.jackson.databind.ObjectMapper
import io.lettuce.core.RedisChannelHandler
import io.lettuce.core.RedisConnectionStateListener
import io.lettuce.core.event.command.CommandListener
import io.lettuce.core.event.command.CommandStartedEvent
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.net.SocketAddress

@Configuration
class CustomRedisConfiguration(
    private val multipleRedisProps: CustomMultipleRedisProps,
    private val redisProperties: RedisProperties,
    private val objectMapper: ObjectMapper,
) {

    private val log = LoggerFactory.getLogger(javaClass)

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

//        connectionFactory.nativeClient?.addListener(object: CommandListener {
//            override fun commandStarted(event: CommandStartedEvent?) {
//                log.info("@@@@@@ commandStarted")
//            }
//        })
//
//        connectionFactory.nativeClient?.addListener(
//            object: RedisConnectionStateListener {
//                override fun onRedisDisconnected(connection: RedisChannelHandler<*, *>?) {
//                    log.info("@@@@@@ onRedisDisconnected")
//                }
//
//                override fun onRedisExceptionCaught(connection: RedisChannelHandler<*, *>?, cause: Throwable?) {
//                    log.info("@@@@@@ onRedisExceptionCaught")
//                }
//
//                override fun onRedisConnected(connection: RedisChannelHandler<*, *>?, socketAddress: SocketAddress?) {
//                    log.info("@@@@@@ onRedisConnected")
//                }
//            }
//        )

        return StringRedisTemplate(connectionFactory).apply {
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