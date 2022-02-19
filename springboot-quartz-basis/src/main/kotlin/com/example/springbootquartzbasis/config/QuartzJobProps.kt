package com.example.springbootquartzbasis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "custom-worker")
class QuartzJobProps {

    class WorkerProps {
        var id: String? = null
        var jobGroup: String = "default-group"
        var jobDesc: String = "default-desc"
        var triggerGroup: String = "default-trigger-group"
        var triggerDesc: String = "default-trigger-desc"
        var cronExpression: String? = null
    }

    val simpleCronWorker = WorkerProps()
    val simpleWriteWorker = WorkerProps()
    val simpleWriteBulkWorker = WorkerProps()
    val simpleErrorWorker = WorkerProps()
    val simpleGracefulShutdownWorker = WorkerProps()
}