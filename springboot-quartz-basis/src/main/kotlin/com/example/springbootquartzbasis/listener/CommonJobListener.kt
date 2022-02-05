package com.example.springbootquartzbasis.listener

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener
import org.slf4j.LoggerFactory

/**
 * quartz 잡 실행 여부에 따라 이후 호출되는 메소드
 * - 별도의 알람이 가는게 좋을듯하다.
 *
 * [순서]
 * jobToBeExecuted() -> jobWasExecuted() 형태로 간다.
 * 중간에 에러가 발생하면 jobExecutionVetoed() 가 호출된다.
 */
class CommonJobListener : JobListener {

    companion object {
        private const val TO_BE_EXECUTE = "TO_BE_EXECUTE"
        private const val EXECUTION_VETOED = "EXECUTION_VETOED"
        private const val WAS_EXECUTED = "WAS_EXECUTED"
    }

    private val log = LoggerFactory.getLogger(javaClass)

    override fun getName(): String {
        return "=== [CommonJobListener] ==="
    }

    /**
     * 잡이 실행할때 호출됨 (tobe -> was executed)
     */
    override fun jobToBeExecuted(context: JobExecutionContext?) {
        return
    }

    /**
     * 잡이 실행되지 않았을 때(거부되었을 때) 호출됨
     */
    override fun jobExecutionVetoed(context: JobExecutionContext?) {
        loggingListen(EXECUTION_VETOED, context)
    }

    /**
     * 잡이 실행되고 난 뒤 호출됨 (tobe -> was executed)
     */
    override fun jobWasExecuted(context: JobExecutionContext?, jobException: JobExecutionException?) {
        loggingListen(WAS_EXECUTED, context, jobException)
    }

    private fun loggingListen(currentStatus: String, context: JobExecutionContext? = null, jobException: JobExecutionException? = null) {
        val lines = StringBuilder()
        lines.appendLine()
        lines.appendLine("====> Job listener [$currentStatus] start ====>")

        // 기본
        context?.also {
            lines.appendLine("- jobKey :: ${context.jobDetail.key}")
            lines.appendLine("- jobDesc :: ${context.jobDetail.description}")
            lines.appendLine("- jobData :: ${context.mergedJobDataMap.toMap()}")
            lines.appendLine("- isDurable :: ${context.jobDetail.isDurable}")
        }

        // exception
        jobException?.also { ex ->
            lines.appendLine("- exception-message : ${ex.message}")
        }
        lines.appendLine("====> Job listener [$currentStatus] end ====>")

        log.info(lines.toString())
    }
}