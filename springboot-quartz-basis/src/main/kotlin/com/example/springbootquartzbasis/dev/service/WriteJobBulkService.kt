package com.example.springbootquartzbasis.dev.service

import com.example.springbootquartzbasis.config.QuartzJobProps
import com.example.springbootquartzbasis.config.toJson
import com.example.springbootquartzbasis.sample.WriteBulkJob
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
class WriteJobBulkService(
    quartzProps: QuartzJobProps,
    private val scheduler: Scheduler
) {

    private val simpleWriteBulkWorker = quartzProps.simpleWriteBulkWorker
    private val jobDetail: JobDetail = JobBuilder.newJob()
        .ofType(WriteBulkJob::class.java)
        .withIdentity(simpleWriteBulkWorker.id!!, simpleWriteBulkWorker.jobGroup)
        .withDescription(simpleWriteBulkWorker.jobDesc)
        .storeDurably(false)
        .requestRecovery()
        .build()

    fun process(bulk: Int, size: Int) {
        val lines: MutableList<String> = mutableListOf()

        for (seq in 1..size) {
            val line = if (seq == size) {
                "${nowLocalDateTime()}-$seq ----- end -----"
            } else {
                "${nowLocalDateTime()}-$seq"
            }
            lines.add(line)
        }

        // 하나의 작업에 대해서 하나의 트리거를 만들기보다는,
        // 다수의 작업이 동일 시간대에 실행되기를 원한다면 jobDataMap 으로 해당 데이터를 전부/일부 전달해서 벌크로 처리할 수 있도록 한다.
        // 성능상에서 writeJobService 보다 더 뛰어남. : jobData 의 데이터 타입은 blob 로 잡혀있다.
        val jobDataMap = JobDataMap().apply {
            this["lines"] = lines.toJson()
        }

        val trigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("${simpleWriteBulkWorker.id!!}-${nowLocalDateTime()}", simpleWriteBulkWorker.triggerGroup)
            .withDescription(simpleWriteBulkWorker.triggerDesc)
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