package com.example.springbootquartzbasis.listener

import com.example.springbootquartzbasis.util.convertLocalDateTime
import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerListener
import org.slf4j.LoggerFactory

class CommonTriggerListener : TriggerListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun getName(): String {
        return "=== [CommonTriggerListener] ==="
    }

    /**
     * 트리거가 실행된 상태
     */
    override fun triggerFired(trigger: Trigger?, context: JobExecutionContext?) {
        return
    }

    /**
     * 트리거 중단여부를 확인
     * - true : Job 의 execute method 를 수행하지 않는다.
     * - false : Job 을 수행함
     */
    override fun vetoJobExecution(trigger: Trigger?, context: JobExecutionContext?): Boolean {
        return false
    }

    /**
     * 트리거 중단된 상태
     */
    override fun triggerMisfired(trigger: Trigger?) {
        return
    }

    /**
     * 트리거 완료된 상태
     */
    override fun triggerComplete(
        trigger: Trigger?,
        context: JobExecutionContext?,
        triggerInstructionCode: Trigger.CompletedExecutionInstruction?,
    ) {
        val lines = StringBuilder()
        lines.appendLine()
        lines.appendLine("====> Trigger listener [completed] start ====>")

        trigger?.also {
            lines.appendLine("- triggerJobKey :: ${trigger.jobKey}")
            lines.appendLine("- triggerKey :: ${trigger.key}")
            lines.appendLine("- triggerDesc :: ${trigger.description}")
            lines.appendLine("- triggerData :: ${trigger.jobDataMap.toMap()}")
            lines.appendLine("- triggerNextFireTime :: ${trigger.nextFireTime?.convertLocalDateTime()}")
        }

        lines.appendLine("====> Trigger listener [completed] end ====>")

        log.info(lines.toString())
    }
}