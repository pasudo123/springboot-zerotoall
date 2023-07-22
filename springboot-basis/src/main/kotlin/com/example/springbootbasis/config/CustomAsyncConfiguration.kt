package com.example.springbootbasis.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class CustomAsyncConfiguration: AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.setThreadNamePrefix("pasudoAsync-")
            this.corePoolSize = 1
            this.maxPoolSize = 5
            this.setQueueCapacity(10)
//            this.keepAliveSeconds = 2
//            this.setAllowCoreThreadTimeOut(true)
            this.initialize()
        }
    }
}