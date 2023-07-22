package com.example.springbootbasis.api.scheduler

import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class DynamicTask(
    val taskService: TaskService,
    val name: String,
    val properties: Map<String, Any> = emptyMap(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : Runnable {

    lateinit var jobId: String
    private val log = LoggerFactory.getLogger(javaClass)

    override fun run() {
        taskService.doSomething(name, jobId)
    }
}