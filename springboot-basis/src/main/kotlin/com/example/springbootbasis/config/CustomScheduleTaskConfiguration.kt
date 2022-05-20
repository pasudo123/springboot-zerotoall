package com.example.springbootbasis.config

import com.example.springbootbasis.exception.CustomScheduleErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class CustomScheduleTaskConfiguration {

    @Bean
    fun taskScheduler(): TaskScheduler {
        return ThreadPoolTaskScheduler().apply {
            this.poolSize = 10
            this.setThreadNamePrefix("pasudoSche-")
            this.setErrorHandler(errorHandler())
        }
    }

    @Bean
    fun errorHandler(): CustomScheduleErrorHandler {
        return CustomScheduleErrorHandler()
    }
}