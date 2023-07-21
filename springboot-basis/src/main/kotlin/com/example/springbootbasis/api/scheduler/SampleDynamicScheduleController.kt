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
    private val dynamicTaskRegister: DynamicTaskRegister
) {

    @PostMapping
    fun addScheduler(
        @RequestBody request: DynamicTaskRequest
    ) {
        dynamicTaskRegister.register(request)
    }

    @DeleteMapping("{jobId}")
    fun deleteScheduler(
        @PathVariable jobId: String
    ) {
        dynamicTaskRegister.delete(jobId)
    }

    data class DynamicTaskRequest(
        val name: String,
        val properties: Map<String, Any> = emptyMap()
    ) {

        fun toDynamicTask(): DynamicTask {
            return DynamicTask(
                name = this.name,
                properties = this.properties
            )
        }
    }
}