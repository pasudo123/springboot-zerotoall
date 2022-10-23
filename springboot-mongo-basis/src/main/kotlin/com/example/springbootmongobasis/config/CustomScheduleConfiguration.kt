package com.example.springbootmongobasis.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
@EnableScheduling
class CustomScheduleConfiguration : SchedulingConfigurer {

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {

        val taskScheduler = ThreadPoolTaskScheduler().apply {
            this.poolSize = 10
            this.setThreadNamePrefix("mongo-project-scheduler")
            this.initialize()
        }

        taskRegistrar.setTaskScheduler(taskScheduler)
    }
}
