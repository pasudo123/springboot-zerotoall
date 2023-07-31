package com.example.springbootconcurrencyv2basis.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class CustomRedisConfiguration(
    private val redisProperties: RedisProperties,
) {

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

//    @Bean
//    fun jedisConnectionFactory(): JedisConnectionFactory {
//        return JedisConnectionFactory(
//            RedisStandaloneConfiguration(redisProperties.host, redisProperties.port).apply {
//                this.database = 0
//            }
//        )
//    }
//
//    @Bean
//    fun bagRedisJedisTemplate(): RedisTemplate<String, String> {
//        val connectionFactory = jedisConnectionFactory().apply {
//            this.afterPropertiesSet()
//        }
//
//        return RedisTemplate<String, String>().apply {
//            setConnectionFactory(connectionFactory)
//            this.keySerializer = StringRedisSerializer()
//            this.valueSerializer = StringRedisSerializer()
//            this.hashKeySerializer = StringRedisSerializer()
//            this.hashValueSerializer = StringRedisSerializer()
//            this.afterPropertiesSet()
//        }
//    }

    @Bean
    fun bagRedisLettuceTemplate(): RedisTemplate<String, String> {
        val connectionFactory = lettuceConnectionFactory().apply {
            this.database = 0
            this.afterPropertiesSet()
        }

        return RedisTemplate<String, String>().apply {
            setConnectionFactory(connectionFactory)
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = StringRedisSerializer()
            this.afterPropertiesSet()
        }
    }

    @Bean
    fun stringRedisTemplate(): StringRedisTemplate {
        val connectionFactory = lettuceConnectionFactory().apply {
            this.database = 0
            this.afterPropertiesSet()
        }

        return StringRedisTemplate().apply {
            setConnectionFactory(connectionFactory)
            this.afterPropertiesSet()
        }
    }
}
