package com.example.springbootquartzbasis.config

import com.example.springbootquartzbasis.sample.SampleJobVersion01
import com.example.springbootquartzbasis.util.seoulTimeZone
import org.quartz.CronScheduleBuilder
import org.quartz.CronTrigger
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import javax.annotation.PostConstruct

@Configuration
class QuartzConfiguration(
    private val quartzProps: QuartzJobProps
) {

    private lateinit var simpleCronWorker: QuartzJobProps.SimpleCronWorker

    @PostConstruct
    fun init() {
        simpleCronWorker = quartzProps.simpleCronWorker
    }

    @Bean("simpleJobDetail")
    fun simpleJobDetail(): JobDetail {
        return JobBuilder.newJob()
            .ofType(SampleJobVersion01::class.java)
            .withIdentity(simpleCronWorker.id!!, simpleCronWorker.jobGroup)
            .withDescription(simpleCronWorker.jobDesc)
            // trigger 에 의해 수행되지 않더라도(잡은 고아상태, orphaned) 잡 인스턴스는 남겨놓음 -> false 면 execute 가 완료되고 이후에 gc 에 의해서 처리됨
            .storeDurably(true)
            // 잡이 문제가 되는 상황에 대해서 리커버리를 할 지 여부를 결정한다.
            .requestRecovery(true)
            .build()
    }

    @Bean("simpleJobTrigger")
    fun simpleJobTrigger(): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(simpleJobDetail())
            .withIdentity(simpleCronWorker.id!!, simpleCronWorker.triggerGroup)
            .withDescription(simpleCronWorker.triggerDesc)
            .withSchedule(
                // 크론 실행 (한국시간)
                CronScheduleBuilder
                    .cronSchedule(simpleCronWorker.cronExpression)
                    .inTimeZone(seoulTimeZone())
            )
            .build()
    }

    /**
     * QuartzAutoConfiguration 에서
     * scheduleFactoryBean 을 통해 빈을 사전에 만들어서, scheduler 도 빈으로 등록된 상태.
     */
    @Bean
    fun scheduler(
        @Qualifier("simpleJobDetail")
        jobDetail: JobDetail,
        @Qualifier("simpleJobTrigger")
        trigger: Trigger,
        factory: SchedulerFactoryBean
    ): Scheduler {

        // listener 등록
        factory.scheduler.apply {
            // 참고 : http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-07.html
            // 잡과 트리거에 대해서 리스너를 등록할 수 있다.
//            this.listenerManager.addJobListener(CommonJobListener(), allJobs())
//            this.listenerManager.addTriggerListener(CommonTriggerListener(), allTriggers())
        }

        // 잡 키 검사
        if (factory.scheduler.checkExists(jobDetail.key)) {

            if (factory.scheduler.isEqualCronExpression(jobDetail, simpleCronWorker.cronExpression!!)) {
                return factory.scheduler
            }

            factory.scheduler.deleteJob(jobDetail.key)
        }

        return factory.scheduler.apply {
            this.scheduleJob(jobDetail, trigger)
        }
    }
}

private fun Scheduler.isEqualCronExpression(jobDetail: JobDetail, cronExpression: String): Boolean {
    val storeTriggers = this.getTriggersOfJob(jobDetail.key)

    for (trigger in storeTriggers) {
        if (trigger !is CronTrigger) {
            continue
        }

        return trigger.cronExpression == cronExpression
    }

    return false
}