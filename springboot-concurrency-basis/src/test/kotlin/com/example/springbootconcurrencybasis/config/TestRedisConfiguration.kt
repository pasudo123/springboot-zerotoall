package com.example.springbootconcurrencybasis.config

import com.google.common.io.Resources
import mu.KLogging
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisExecProvider
import redis.embedded.RedisServerBuilder
import redis.embedded.util.OS
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * https://github.com/ozimov/embedded-redis
 */
@TestConfiguration
class TestRedisConfiguration(
    redisProperties: RedisProperties,
) {

    companion object : KLogging()

    // geo operation 을 이용해주기 위해서 해당 버전을 올려준다.
    private val customProvider = RedisExecProvider.defaultProvider()
        .override(OS.MAC_OS_X, Resources.getResource("redis-server-5.0.12").file)

    private val redisServer = RedisServerBuilder()
        .redisExecProvider(customProvider)
        .port(redisProperties.port)
        .build()

    @PostConstruct
    fun postConstruct() {
        logger.info { "== redis-server start" }
        redisServer.start();
    }

    @PreDestroy
    fun preDestroy() {
        logger.info { "== redis-server stop" }
        redisServer.stop();
    }
}