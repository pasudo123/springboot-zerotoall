package com.example.springboot3jobrunnerbasis

import com.example.springboot3jobrunnerbasis.task.LongTermTask
import com.example.springboot3jobrunnerbasis.task.ShortTermTask
import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CustomCommandLineRunner(
    private val shortTermTask: ShortTermTask,
    private val longTermTask: LongTermTask,
    private val jobScheduler: JobScheduler
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        log.info("### run")

        // 즉시 수행
        // jobScheduler.enqueue { shortTermTask.task("fire & forgot") }
        jobScheduler.enqueue { longTermTask.task() }

        // 스케줄링 설정 후 실행 : 명확한 시간판단은 어렵다
        // jobScheduler.schedule(LocalDateTime.now().plusSeconds(30L)) { shortTermTask.task("30초 schedule") }

        // 시간이 너무 짧으면 런타임 에러가 발생함 : The smallest interval for recurring jobs is 5 seconds
        jobScheduler.scheduleRecurrently("*/3 * * * * *") { shortTermTask.task("every 5 seconds") }
    }
}