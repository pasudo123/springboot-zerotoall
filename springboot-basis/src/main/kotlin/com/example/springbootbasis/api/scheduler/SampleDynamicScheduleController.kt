package com.example.springbootbasis.api.scheduler

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("sample-scheduler")
class SampleDynamicScheduleController(
    private val dynamicTaskRegisterer: DynamicTaskRegisterer
) {

    @PostMapping
    fun addScheduler(
        @RequestBody request: DynamicTaskRequest
    ) {
        dynamicTaskRegisterer.register(request)
    }

    @DeleteMapping("{jobId}")
    fun deleteScheduler(
        @PathVariable jobId: String
    ) {
        dynamicTaskRegisterer.delete(jobId)
    }

    data class DynamicTaskRequest(
        val name: String,
        val properties: Map<String, Any> = emptyMap()
    ) {

        fun toDynamicTask(taskService: TaskService): DynamicTask {
            return DynamicTask(
                taskService,
                name = this.name,
                properties = this.properties
            )
        }
    }
}