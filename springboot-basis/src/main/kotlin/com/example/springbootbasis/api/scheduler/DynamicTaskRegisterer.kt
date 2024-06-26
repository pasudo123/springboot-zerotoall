package com.example.springbootbasis.api.scheduler

import com.example.springbootbasis.config.toJson
import org.slf4j.LoggerFactory
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import java.util.TimeZone
import java.util.UUID
import java.util.concurrent.ScheduledFuture

@Component
class DynamicTaskRegisterer(
    private val taskScheduler: TaskScheduler,
    private val currentPoolTaskExecutor: ThreadPoolTaskExecutor,
    private val taskService: TaskService
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val jobGroup: MutableMap<String, ScheduledFuture<*>> = mutableMapOf()
    private val seoulTimeZone = TimeZone.getTimeZone("Asia/Seoul")

    fun register(
        request: SampleDynamicScheduleController.DynamicTaskRequest,
        jobId: String = UUID.randomUUID().toString()
    ) {
        log.info("# register task: ${request.toJson()}")

        val dynamicTask = request.toDynamicTask(taskService).apply {
            this.threadPoolTaskExecutor = currentPoolTaskExecutor
            this.jobId = jobId
        }

        try {
            val scheduledFuture = taskScheduler.schedule(
                dynamicTask,
                CronTrigger(cronExpression(), seoulTimeZone)
            ) ?: return
            jobGroup[jobId] = scheduledFuture
        } catch (exception: Exception) {
            log.error("register exception : ${exception.message}")
        }
    }

    fun findAll(): Map<String, Any> {
        return jobGroup
    }

    fun delete(
        jobId: String
    ) {
        val scheduledFuture = jobGroup.remove(jobId) ?: return
        scheduledFuture.cancel(true)
    }

    fun deleteAll() {
        val jobIds = jobGroup.keys.map { it }
        jobIds.forEach { delete(it) }
    }

    /**
     * 갯수별로 cron expression 을 달리 가져감
     * 0개, 5개 -> 0/5
     * 1개 -> 1/5
     * 2개 -> 2/5
     * 3개 -> 3/5
     * 4개 -> 4/5
     */
    // private fun cronExpression(): String = "${this.jobGroup.size % 5}/5 * * * * *"
    private fun cronExpression(): String = "0/5 * * * * *"
}
