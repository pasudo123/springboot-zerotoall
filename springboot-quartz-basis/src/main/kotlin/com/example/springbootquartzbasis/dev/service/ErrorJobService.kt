package com.example.springbootquartzbasis.dev.service

import com.example.springbootquartzbasis.config.QuartzJobProps
import com.example.springbootquartzbasis.sample.ErrorJob
import com.example.springbootquartzbasis.util.convertDate
import com.example.springbootquartzbasis.util.nowLocalDateTime
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ErrorJobService(
    quartzProps: QuartzJobProps,
    private val scheduler: Scheduler
) {

    private val simpleErrorWorker = quartzProps.simpleErrorWorker
    private val jobDetail: JobDetail = JobBuilder.newJob()
        .ofType(ErrorJob::class.java)
        .withIdentity(simpleErrorWorker.id!!, simpleErrorWorker.jobGroup)
        .withDescription(simpleErrorWorker.jobDesc)
        .storeDurably(false)
        .requestRecovery()
        .build()

    fun process(condition: Boolean) {
        val uuid = UUID.randomUUID().toString()

        val jobDataMap = JobDataMap().apply {
            this["data"] = uuid
            this["condition"] = condition.toString()
        }

        val trigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("${simpleErrorWorker.id!!}-$uuid", simpleErrorWorker.triggerGroup)
            .withDescription(simpleErrorWorker.triggerDesc)
            .usingJobData(jobDataMap)
            .withSchedule(
                SimpleScheduleBuilder
                    .simpleSchedule()
                    .withMisfireHandlingInstructionFireNow()
            )
            .startAt(nowLocalDateTime().plusSeconds(3).convertDate())
            .build()

        if (scheduler.checkExists(jobDetail.key).not()) {
            scheduler.scheduleJob(jobDetail, trigger)
        } else {
            scheduler.scheduleJob(trigger)
        }
    }
}