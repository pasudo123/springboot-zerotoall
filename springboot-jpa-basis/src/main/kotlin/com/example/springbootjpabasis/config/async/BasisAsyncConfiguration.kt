package com.example.springbootjpabasis.config.async

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class BasisAsyncConfiguration: AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.setThreadNamePrefix("pasudoAsync-")
            this.corePoolSize = 4
            this.maxPoolSize = 10
            this.setQueueCapacity(1000)
            this.setRejectedExecutionHandler(ThreadPoolExecutor.AbortPolicy())
            this.keepAliveSeconds = 2
            this.setAllowCoreThreadTimeOut(true)
            this.initialize()
        }
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return BasisAsyncErrorHandler()
    }
}