package com.example.springbootquartzbasis.sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter

@Component
@DisallowConcurrentExecution
class WriteJob : QuartzJobBean() {

    private val log = LoggerFactory.getLogger(javaClass)
    private val file = File("./springboot-quartz-basis/file/write-job-execute.log").also {
        it.parentFile.mkdirs()
    }
    private val fileWriter = FileWriter(file.path, true)

    override fun executeInternal(context: JobExecutionContext) {

        CoroutineScope(Dispatchers.IO).async {
            log.info("[call] [call] [call] [call] >>>>>>>>>>>>")

            val jobDataMap = context.mergedJobDataMap
            val data = jobDataMap["data"]

            try {
                fileWriter.appendLine("====> $data")
            } catch (exception: Exception) {
            } finally {
                fileWriter.close()
            }
        }
    }
}