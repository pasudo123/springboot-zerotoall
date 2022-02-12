package com.example.springbootquartzbasis.sample

import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.io.FileWriter

@Component
@DisallowConcurrentExecution
class WriteJob : QuartzJobBean() {

    // 절대경로로 가자. 클래스패스에 안써짐.
    private val log = LoggerFactory.getLogger(javaClass)
    private val fileWriter = FileWriter("./springboot-quartz-basis/job-execute.log", true)

    override fun executeInternal(context: JobExecutionContext) {

        log.info("[call] [call] [call] [call] >>>>>>>>>>>>")

        val jobDataMap = context.mergedJobDataMap
        val data = jobDataMap["data"]

        try {
            fileWriter.appendLine("====> $data")
            fileWriter.close()
        } catch (exception: Exception) {}
    }
}