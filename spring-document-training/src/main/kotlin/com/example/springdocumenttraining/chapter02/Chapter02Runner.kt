package com.example.springdocumenttraining.chapter02

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class Chapter02Runner(
    private val applicationContext: ApplicationContext,
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {

        println("chapter02 runner >>>")

        applicationContext.getBean(Chapter02LazyConfiguration.MotherLazyBean::class.java).run {
            this.use()
        }

        applicationContext.getBean(Chapter02LazyConfiguration.MotherNowBean::class.java).run {
            this.use()
        }
    }
}