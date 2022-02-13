package com.example.springbootquartzbasis.sample

import org.quartz.DisallowConcurrentExecution
import org.quartz.InterruptableJob
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter

@Component
@DisallowConcurrentExecution
class GracefulShutdownJob : QuartzJobBean(), InterruptableJob {

    private val log = LoggerFactory.getLogger(javaClass)
    private val file = File("./springboot-quartz-basis/file/graceful-shutdown-job-execute.log").also {
        it.parentFile.mkdirs()
    }
    private val fileWriter = FileWriter(file.path, true)

    override fun executeInternal(context: JobExecutionContext) {

        log.info("+++++ [call] [call] [call] [call] +++++")

        val jobDataMap = context.mergedJobDataMap
        val data = jobDataMap["data"]

        try {
            Thread.sleep(500)
            fileWriter.appendLine("====> $data")
        } catch (exception: Exception) {
        } finally {
            fileWriter.close()
        }
    }

    override fun interrupt() {
        log.error("스케줄 인터럽트")
    }
}