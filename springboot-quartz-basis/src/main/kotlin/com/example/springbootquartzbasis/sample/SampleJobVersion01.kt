package com.example.springbootquartzbasis.sample

import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct

@Component
class SampleJobVersion01 : QuartzJobBean() {

    private val currentValue = AtomicInteger()
    private val log = LoggerFactory.getLogger(javaClass)

    init {
        log.info("init block")
    }

    @PostConstruct
    fun init() {
        log.info("post construct")
    }

    override fun executeInternal(context: JobExecutionContext) {
        val lines = StringBuilder()
        lines.appendLine()
        lines.appendLine("++++++++++++++++++++++++++++++++++++++++++++++++")
        lines.appendLine("+++++ execute job : ${this.javaClass.name}")
        lines.appendLine("+++++ variable : ${currentValue.getAndIncrement()}")
        lines.appendLine("++++++++++++++++++++++++++++++++++++++++++++++++")

        log.info(lines.toString())
    }
}