package com.example.springbootquartzbasis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "custom-worker")
class QuartzJobProps {

    val simpleCronWorker = SimpleCronWorker()

    class SimpleCronWorker {
        var id: String? = null
        var jobGroup: String = "default-group"
        var jobDesc: String = "default-desc"
        var triggerGroup: String = "default-trigger-group"
        var triggerDesc: String = "default-trigger-desc"
        var cronExpression: String? = null
    }
}