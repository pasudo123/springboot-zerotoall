package com.example.springbootredisbasis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
class CustomMultipleRedisProps {

    var redisGroup: MutableList<CustomRedisNode> = mutableListOf()

    class CustomRedisNode(
        var host: String? = null,
        var port: Int? = null,
        var database: Int? = null,
        var password: String? = null
    ) {
        fun getRedisPassword(): RedisPassword {
            return if (password.isNullOrBlank()) RedisPassword.none() else RedisPassword.of(password)
        }
    }
}