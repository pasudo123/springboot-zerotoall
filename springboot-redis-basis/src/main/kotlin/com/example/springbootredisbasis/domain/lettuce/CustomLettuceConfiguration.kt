package com.example.springbootredisbasis.domain.lettuce

import io.lettuce.core.RedisChannelHandler
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisConnectionStateListener
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.SocketAddress

@Configuration
class CustomLettuceConfiguration(
    private val redisProperties: RedisProperties
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun redisClient(): RedisClient {
        return RedisClient.create("redis://${redisProperties.host}:${redisProperties.port}/0").apply {
            this.addListener(object: RedisConnectionStateListener {
                override fun onRedisConnected(connection: RedisChannelHandler<*, *>?, socketAddress: SocketAddress?) {
                    log.info("#### onRedisConnected")
                    super.onRedisConnected(connection, socketAddress)
                }

                override fun onRedisDisconnected(connection: RedisChannelHandler<*, *>?) {
                    log.error("#### onRedisDisconnected")
                }

                override fun onRedisExceptionCaught(connection: RedisChannelHandler<*, *>?, cause: Throwable?) {
                    log.error("#### onRedisExceptionCaught")
                }
            })
        }
    }
}