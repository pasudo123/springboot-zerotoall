package com.example.springbootquartzbasis.sample

import com.example.springbootquartzbasis.config.toList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileWriter

@Component
class WriteBulkJob : QuartzJobBean() {

    private val log = LoggerFactory.getLogger(javaClass)
    private val file = File("./springboot-quartz-basis/file/write-bulk-job-execute.log").also {
        it.parentFile.mkdirs()
    }
    private val fileWriter = FileWriter(file.path, true)

    override fun executeInternal(context: JobExecutionContext) {

        CoroutineScope(Dispatchers.IO).async {
            log.info("[call] [call] [call] [call] >>>>>>>>>>>>")

            val jobDataMap = context.mergedJobDataMap
            val lines = (jobDataMap["lines"] as String).toList<String>()

            try {
                lines.forEach { line ->
                    fileWriter.appendLine("====> $line")
                }
            } catch (exception: Exception) {}
            finally {
                fileWriter.close()
            }
        }
    }
}