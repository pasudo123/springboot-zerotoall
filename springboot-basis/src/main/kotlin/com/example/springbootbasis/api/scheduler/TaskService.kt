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
//        throw RuntimeException("hello")
//        log.info("@@ start run name=$name, jobId=$jobId")
        // log.info("## start run name=$name, ququeSize=${threadPoolTaskExecutor.threadPoolExecutor.queue.size}, activeCount=${threadPoolTaskExecutor.activeCount}, poolSize=${threadPoolTaskExecutor.poolSize}")
        task(name, jobId)
        log.info("@@ end run name=$name")
    }

    private fun task(
        name: String,
        jobId: String
    ) {
        Thread.sleep(6000)
//        log.info("process name=$name, jobId=$jobId")
    }
}
