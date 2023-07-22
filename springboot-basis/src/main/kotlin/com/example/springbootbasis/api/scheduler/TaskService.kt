package com.example.springbootbasis.api.scheduler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class TaskService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Async
    fun doSomething(
        name: String,
        jobId: String
    ) {
        log.info("@@ start run name=$name, jobId=$jobId")
        task(name, jobId)
        log.info("@@ end run name=$name, jobId=$jobId")
    }

    private fun task(
        name: String,
        jobId: String
    ) {
        log.info("process name=$name, jobId=$jobId")
        Thread.sleep(5000)
    }
}