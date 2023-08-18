package com.example.springbootredisbasis.config

import io.lettuce.core.event.connection.ConnectEvent
import io.lettuce.core.event.connection.DisconnectedEvent
import io.lettuce.core.event.connection.ReconnectAttemptEvent
import io.lettuce.core.event.connection.ReconnectFailedEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * 동작은 안합니다..
 */
@Component
class RedisConnectionWatcher {

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun handleConnect(event: ConnectEvent) {
        log.info("##### Lettuce reconnected to Redis.")
    }

    @EventListener
    fun handleReconnectFailed(event: ReconnectFailedEvent) {
        log.info("##### handleReconnectFailed : ${event.cause.message}")
    }

    @EventListener
    fun handleReconnectAttempt(event: ReconnectAttemptEvent) {
        log.info("##### handleReconnectAttempt : ${event.attempt}")
    }

    @EventListener
    fun handleDisconnectedEvent(event: DisconnectedEvent) {
        log.info("##### handleDisconnectedEvent : ${event.remoteAddress()}")
    }
}
