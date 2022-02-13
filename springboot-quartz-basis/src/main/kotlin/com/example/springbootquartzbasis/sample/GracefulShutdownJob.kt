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

    /**
     * 아래 잡을 수행하면서, 중간에 shutdown 이 되는 경우
     * - 셧다운 잘된다. (디비에 해당 트리거가 남겨져서 실행이 잘되는걸로 보임.)
     *
     * 다만 출력내용이 클러스터 여부에 따라 다르다.
     * - org.quartz.jobStore.isClustered:true
     *  - ClusterManager: ......Scheduled 1 recoverable job(s) for recovery
     *
     * - org.quartz.jobStore.isClustered:false
     *  - Recovering 1 jobs that were in-progress at the time of the last shut-down
     *
     */
    override fun executeInternal(context: JobExecutionContext) {

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