package com.example.springbootbasis.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class CustomAsyncConfiguration: AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.setThreadNamePrefix("pasudoAsync-")
            this.corePoolSize = 1
            this.maxPoolSize = 1
            this.setQueueCapacity(1)
            this.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
            this.keepAliveSeconds = 2
            this.setAllowCoreThreadTimeOut(true)
            this.initialize()
        }
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return CustomAsyncErrorHandler()
    }
}