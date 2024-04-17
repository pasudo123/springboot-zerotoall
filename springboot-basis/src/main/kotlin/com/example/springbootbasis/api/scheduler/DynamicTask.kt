package com.example.springbootbasis.api.scheduler

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.time.LocalDateTime

class DynamicTask(
    val taskService: TaskService,
    val name: String,
    val properties: Map<String, Any> = emptyMap(),
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Runnable {

    lateinit var threadPoolTaskExecutor: ThreadPoolTaskExecutor
    lateinit var jobId: String
    private val log = LoggerFactory.getLogger(javaClass)

    override fun run() {
        taskService.doSomething(name, jobId)
//        log.info("@@ start run name=$name, jobId=$jobId")
//        async(Dispatchers.IO) { doSomething() }
//        log.info("@@ end run name=$name, jobId=$jobId")
    }

    private suspend fun doSomething() {
        delay(5000)
        log.info("process name=$name, jobId=$jobId")
    }
}
