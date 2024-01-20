package com.example.springbootbasis.config.redis

import com.example.springbootbasis.util.EnvUtil.getFirstElementByProperty
import jakarta.annotation.PostConstruct
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory

@Configuration
class ShedLockConfiguration(
    private val env: Environment
) {

    private lateinit var envKey: String

    @PostConstruct
    fun init() {
        // 멀티환경에 다른 동일 스케줄건에 대해서 envKey 로 분리를 해야한다.
        // 전달받은 services args 가 존재한다고 가정
        // --services=apple,banana
        // --services=grape
        // --services=melon
        // 각각이 appleShedKey, grapeShedKey, melonShedKey 를 가진다고 가정.
        this.envKey = "${env.getFirstElementByProperty()}ShedKey"
    }

    @Bean
    fun lockProvider(connectionFactory: RedisConnectionFactory): LockProvider {
        return RedisLockProvider(connectionFactory, envKey)
    }
}