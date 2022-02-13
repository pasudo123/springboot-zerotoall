package com.example.springbootquartzbasis.dev.service

import com.example.springbootquartzbasis.config.QuartzJobProps
import com.example.springbootquartzbasis.sample.WriteJob
import com.example.springbootquartzbasis.util.convertDate
import com.example.springbootquartzbasis.util.nowLocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            // true : QRTZ_JOB_DETAILS 테이블에 로우 남김
            // false : QRTZ_JOB_DETAILS 에 남기지 않음
        .storeDurably(false)
        .requestRecovery()
        .build()

    fun process(size: Int) {
        for (seq in 1..size) {
            doTrigger(seq, size)
        }
    }

    fun processWithAsync(size: Int) {
        for (seq in 1..size) {
            CoroutineScope(Dispatchers.IO).async {
                doTrigger(seq, size)
            }
        }
    }

    /**
     * 동일 잡에 대한 트리거를 여러개 만든다.
     */
    fun doTrigger(seq: Int, size: Int) {
        val data = if (seq == size) {
            "${nowLocalDateTime()}-$seq ----- end -----"
        } else {
            "${nowLocalDateTime()}-$seq"
        }

        val jobDataMap = JobDataMap().apply {
            this["data"] = data
        }

        // 10초 뒤에 트리거를 수행한다.
        val startAt = nowLocalDateTime().plusSeconds(5)

        // QRTZ_TRIGGERS 에 데이터 남기고, TRIGGERS 가 다 실행되면 데이터 남기지 않음
        val trigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail) // 해당 트리거는 어떤 잡에 동작할 것인지가, forJob() 구문에 추가된다.
            .withIdentity("${simpleWriteWorker.id!!}-$data", simpleWriteWorker.triggerGroup)
            .withDescription(simpleWriteWorker.triggerDesc)
            .usingJobData(jobDataMap)
            .withSchedule(
                SimpleScheduleBuilder
                    .simpleSchedule()
                        // 실행불발 시, thread 를 다 쓰고있거나 그러는와중,
                        // 실행이 가능한 상태가 되면, 바로 트리거를 다시 실행한다. -> 그리고 db 에서 삭제
                        // jobStoreSupport 내 removeTrigger(Connection conn, TriggerKey key) 가 호출되면서 해당 트리거는 실행후에 이후 삭제된다.
                    .withMisfireHandlingInstructionFireNow()
            )
            .startAt(startAt.convertDate())
            .build()

        if (scheduler.checkExists(jobDetail.key)
                .not()) {
            // job 이 없을때만 스케줄링.
            scheduler.scheduleJob(jobDetail, trigger)
        } else {
            // job 이 존재하면, trigger 만 스케줄링
            // 동일 identity 면 에러발생, 따라서 trigger 의 id 가 매번 달라야함.
            scheduler.scheduleJob(trigger)
        }
    }
}