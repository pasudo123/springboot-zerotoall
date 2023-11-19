package com.example.springdocumenttraining.chapter02

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ApplicationContextEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class Chapter02Runner(
    private val applicationContext: ApplicationContext,
    private val configurableApplicationContext: ConfigurableApplicationContext
): ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
//        applicationContext.getBean(LifeCycleProcessImpl::class.java)
//        configurableApplicationContext.getBean(LifeCycleProcessImpl::class.java)
    }
}
