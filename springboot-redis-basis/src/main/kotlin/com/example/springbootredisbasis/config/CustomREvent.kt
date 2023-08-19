package com.example.springbootredisbasis.config

import io.lettuce.core.event.Event
import io.lettuce.core.event.connection.ConnectEvent
import io.lettuce.core.event.connection.ConnectedEvent
import io.lettuce.core.event.connection.ConnectionActivatedEvent
import io.lettuce.core.event.connection.ConnectionCreatedEvent
import io.lettuce.core.event.connection.ConnectionDeactivatedEvent
import io.lettuce.core.event.connection.DisconnectedEvent
import io.lettuce.core.event.connection.ReconnectAttemptEvent
import io.lettuce.core.event.connection.ReconnectFailedEvent
import java.net.SocketAddress

data class CustomREvent (
    val event: Event
) {

    private var redisUri: String? = null
    private var remoteAddress: SocketAddress? = null
    private var rState: RState = RState.NONE
    private var info: Any? = null

    init {
        when (event) {
            is ConnectEvent -> {
                this.redisUri = event.redisUri
                this.rState = RState.CONNECT
            }
            is ConnectionCreatedEvent -> {
                this.redisUri = event.redisUri
                this.rState = RState.CONNECTION_CREATED
            }
            is ConnectedEvent -> {
                this.remoteAddress = event.remoteAddress()
                this.rState = RState.CONNECTED
            }
            is ConnectionActivatedEvent -> {
                this.remoteAddress = event.remoteAddress()
                this.rState = RState.CONNECTION_ACTIVATED
            }
            is ConnectionDeactivatedEvent -> {
                this.remoteAddress = event.remoteAddress()
                this.rState = RState.CONNECTION_DEACTIVATED
            }
            is DisconnectedEvent -> {
                this.remoteAddress = event.remoteAddress()
                this.rState = RState.DISCONNECTED
            }
            is ReconnectAttemptEvent -> {
                this.remoteAddress = event.remoteAddress()
                this.rState = RState.RECONNECT_ATTEMPT
            }
            is ReconnectFailedEvent -> {
                this.remoteAddress = event.remoteAddress()
                this.rState = RState.RECONNECT_FAILED
            }
            else -> {
                this.info = event.toJson()
                this.rState = RState.NONE
            }
        }
    }

    fun getRedisUriOrEmpty(): String {
        return this.redisUri ?: ""
    }

    fun getRemoteAddressOrEmpty(): String {
        return this.remoteAddress.toString() ?: ""
    }

    fun getInfoOrNull(): Any? {
        return this.info
    }

    fun currentState(): RState {
        return this.rState
    }

    enum class RState{
        CONNECT,
        CONNECTION_CREATED,
        CONNECTED,
        CONNECTION_ACTIVATED,
        CONNECTION_DEACTIVATED,
        DISCONNECTED,
        RECONNECT_ATTEMPT,
        RECONNECT_FAILED,
        NONE,
    }
}