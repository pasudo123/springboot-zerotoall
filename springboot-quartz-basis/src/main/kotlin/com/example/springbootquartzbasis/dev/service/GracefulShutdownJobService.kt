package com.example.springbootquartzbasis.dev.service

import com.example.springbootquartzbasis.config.QuartzJobProps
import com.example.springbootquartzbasis.sample.GracefulShutdownJob
import com.example.springbootquartzbasis.util.convertDate
import com.example.springbootquartzbasis.util.nowLocalDateTime
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Service

@Service
class GracefulShutdownJobService(
    quartzProps: QuartzJobProps,
    private val scheduler: Scheduler
) {

    private val simpleGracefulShutdownWorker = quartzProps.simpleGracefulShutdownWorker
    private val jobDetail: JobDetail = JobBuilder.newJob()
        .ofType(GracefulShutdownJob::class.java)
        .withIdentity(simpleGracefulShutdownWorker.id!!, simpleGracefulShutdownWorker.jobGroup)
        .withDescription(simpleGracefulShutdownWorker.jobDesc)
        .storeDurably(false)
        .requestRecovery()
        .build()

    fun process(size: Int) {
        for (seq in 1..size) {
            doTrigger(seq, size)
        }
    }

    fun doTrigger(seq: Int, size: Int) {
        val data = if (seq == size) {
            "[graceful-shutdown] ${nowLocalDateTime()}-$seq ----- end -----"
        } else {
            "[graceful-shutdown] ${nowLocalDateTime()}-$seq"
        }

        val jobDataMap = JobDataMap().apply {
            this["data"] = data
        }

        val trigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail) // 해당 트리거는 어떤 잡에 동작할 것인지가, forJob() 구문에 추가된다.
            .withIdentity("${simpleGracefulShutdownWorker.id!!}-$data", simpleGracefulShutdownWorker.triggerGroup)
            .withDescription(simpleGracefulShutdownWorker.triggerDesc)
            .usingJobData(jobDataMap)
            .withSchedule(
                SimpleScheduleBuilder
                    .simpleSchedule()
                    .withMisfireHandlingInstructionFireNow()
            )
            .startAt(nowLocalDateTime().plusSeconds(5).convertDate())
            .build()

        if (scheduler.checkExists(jobDetail.key).not()) {
            scheduler.scheduleJob(jobDetail, trigger)
        } else {
            scheduler.scheduleJob(trigger)
        }
    }
}