package com.example.springbootredisbasis.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.lettuce.core.RedisChannelHandler
import io.lettuce.core.RedisConnectionStateListener
import io.lettuce.core.event.connection.ConnectEvent
import io.lettuce.core.event.connection.ConnectedEvent
import io.lettuce.core.event.connection.ConnectionActivatedEvent
import io.lettuce.core.event.connection.ConnectionCreatedEvent
import io.lettuce.core.event.connection.ConnectionDeactivatedEvent
import io.lettuce.core.event.connection.DisconnectedEvent
import io.lettuce.core.event.connection.ReconnectAttemptEvent
import io.lettuce.core.event.connection.ReconnectFailedEvent
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
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
            // this.applyNotifyListener()
            this.applyEventBus()
        }

        val stringRedisTemplate = StringRedisTemplate(connectionFactory).apply {
            this.afterPropertiesSet()
        }

        return stringRedisTemplate
    }

    /**
     * before 3.4/4.1
     * https://lettuce.io/core/release/reference/#events.before-3.44.1
     */
    private fun LettuceConnectionFactory.applyNotifyListener() {
        this.requiredNativeClient.addListener(object: RedisConnectionStateListener {
            override fun onRedisConnected(connection: RedisChannelHandler<*, *>?, socketAddress: SocketAddress?) {
                log.info("@@@@@@@@@@@@@@@@@@ onRedisConnected")
                super.onRedisConnected(connection, socketAddress)
            }

            override fun onRedisDisconnected(connection: RedisChannelHandler<*, *>?) {
                log.error("@@@@@@@@@@@@@@@@@@ onRedisDisconnected")
            }
            override fun onRedisExceptionCaught(connection: RedisChannelHandler<*, *>?, cause: Throwable?) {}
        })
    }

/**
 * since 3.4/4.1
 * https://lettuce.io/core/release/reference/#events.since-3.44.1
 */
private fun LettuceConnectionFactory.applyEventBus() {
    this.connection
    val eventBus = this.requiredNativeClient.resources.eventBus()
    eventBus.get().subscribe { event ->
        when (event) {
            is ConnectEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ConnectEvent")
            }
            is ConnectedEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ConnectedEvent")
            }
            is ConnectionActivatedEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ConnectionActivatedEvent")
            }
            is ConnectionCreatedEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ConnectionCreatedEvent")
            }
            is ConnectionDeactivatedEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ConnectionDeactivatedEvent")
            }
            is DisconnectedEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ DisconnectedEvent")
            }
            is ReconnectAttemptEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ReconnectAttemptEvent")
            }
            is ReconnectFailedEvent -> {
                log.error("@@@@@@@@@@@@@@@@@@ ReconnectFailedEvent")
            }
            else -> {
                log.error("@@@@@@@@@@@@@@@@@@ [$event]")
            }
        }
    }
}

//    @Bean
//    fun reactiveRedisTemplate(
//        reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory
//    ): ReactiveRedisTemplate<String, Coffee> {
//
//        val keySerializer = StringRedisSerializer()
//        val serializer: Jackson2JsonRedisSerializer<Coffee> = Jackson2JsonRedisSerializer(Coffee::class.java).apply {
//            this.setObjectMapper(objectMapper)
//        }
//
//        val serializerContext = RedisSerializationContext.newSerializationContext<String, Coffee>(serializer).apply {
//            this.key(keySerializer)
//            this.hashKey(keySerializer)
//            this.value(serializer)
//            this.hashValue(serializer)
//        }.build()
//
//        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializerContext)
//    }
}