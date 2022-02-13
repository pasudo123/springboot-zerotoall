package com.example.springbootquartzbasis.sample

import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter

@Component
@DisallowConcurrentExecution
class GracefulShutdownJob : QuartzJobBean() {

    private val log = LoggerFactory.getLogger(javaClass)
    private val file = File("./springboot-quartz-basis/file/graceful-shutdown-job-execute.log").also {
        it.parentFile.mkdirs()
    }
    private val fileWriter = FileWriter(file.path, true)

    override fun executeInternal(context: JobExecutionContext) {

        /**
         * 하나의 잡 수행이 오래걸리게 되는 경우 -> graceful shutdown 은 어떻게 동작해야 하는가?
         * -> 종료되고 재실행되어도 수행이 잘 되고 있음 -> 해당 트리거가 db 에 남아서 그런것도 있는듯 하다.
         */
        try {
            while(true) {
                log.info("+++++ [call] [call] [call] [call] +++++")

                val jobDataMap = context.mergedJobDataMap
                val data = jobDataMap["data"]
                Thread.sleep(5000)
                fileWriter.appendLine("====> $data")
            }
        } catch (exception: Exception) {
            log.error("error : ${exception.message}")
        } finally {
            fileWriter.close()
        }
    }
}