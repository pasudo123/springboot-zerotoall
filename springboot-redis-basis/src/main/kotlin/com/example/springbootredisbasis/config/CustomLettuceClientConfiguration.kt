package com.example.springbootredisbasis.config

import io.lettuce.core.ClientOptions
import io.lettuce.core.ReadFrom
import io.lettuce.core.RedisURI
import io.lettuce.core.resource.ClientResources
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import java.time.Duration
import java.util.Optional

/**
 * implementation of {@link MutableLettuceClientConfiguration}.
 */
class CustomLettuceClientConfiguration: LettuceClientConfiguration {

    private val useSsl = false
    private val verifyPeer = true
    private val startTls = false
    private val clientResources: ClientResources = ClientResources.create()
    private val clientName: String? = null
    private val timeout = Duration.ofSeconds(RedisURI.DEFAULT_TIMEOUT)
    private val shutdownTimeout = Duration.ofMillis(100)

    override fun isUseSsl(): Boolean {
        return useSsl
    }

    override fun isVerifyPeer(): Boolean {
        return verifyPeer
    }

    override fun isStartTls(): Boolean {
        return startTls
    }

    override fun getClientResources(): Optional<ClientResources> {
        return Optional.ofNullable(clientResources)
    }

    override fun getClientOptions(): Optional<ClientOptions> {
        return Optional.empty()
    }

    override fun getClientName(): Optional<String> {
        return Optional.ofNullable(clientName)
    }

    override fun getReadFrom(): Optional<ReadFrom> {
        return Optional.empty()
    }

    override fun getCommandTimeout(): Duration {
        return timeout
    }

    override fun getShutdownTimeout(): Duration {
        return shutdownTimeout
    }

    override fun getShutdownQuietPeriod(): Duration {
        return shutdownTimeout
    }
}