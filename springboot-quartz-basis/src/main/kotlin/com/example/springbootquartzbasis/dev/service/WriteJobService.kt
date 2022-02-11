package com.example.springbootquartzbasis.dev.service

import com.example.springbootquartzbasis.config.QuartzJobProps
import com.example.springbootquartzbasis.sample.WriteJob
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
class WriteJobService(
    quartzProps: QuartzJobProps,
    private val scheduler: Scheduler
) {

    private val simpleWriteWorker = quartzProps.simpleWriteWorker
    private val jobDetail: JobDetail = JobBuilder.newJob()
        .ofType(WriteJob::class.java)
        .withIdentity(simpleWriteWorker.id!!, simpleWriteWorker.jobGroup)
        .withDescription(simpleWriteWorker.jobDesc)
        .storeDurably(false)
        .requestRecovery()
        .build()

    fun process(size: Int) {

        // 동일 잡에 대한 trigger 를 여러개 만든다.
        repeat(size) {

            val data = "${nowLocalDateTime()}-$it"
            val jobDataMap = JobDataMap().apply {
                this["data"] = data
            }

            // 10초 뒤에 트리거를 수행한다.
            val startAt = nowLocalDateTime().plusSeconds(10)

            val trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail) // 해당 트리거는 어떤 잡에 동작할 것인지가, forJob() 구문에 추가된다.
                .withIdentity("${simpleWriteWorker.id!!}-$data", simpleWriteWorker.triggerGroup)
                .withDescription(simpleWriteWorker.triggerDesc)
                .usingJobData(jobDataMap)
                .withSchedule(
                    SimpleScheduleBuilder
                        .simpleSchedule()
                        .withMisfireHandlingInstructionFireNow()
                )
                .startAt(startAt.convertDate())
                .build()

            // job 이 없을때만 스케줄링.
            if (scheduler.checkExists(jobDetail.key).not()) {
                scheduler.scheduleJob(jobDetail, trigger)
            } else {
                // job 이 존재하면, trigger 만 스케줄링
                // 동일 identity 면 에러발생, 따라서 trigger 의 id 가 매번 달라야함.
                scheduler.scheduleJob(trigger)
            }
        }
    }
}