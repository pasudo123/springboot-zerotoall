package com.example.springbootbasis.config

import com.example.springbootbasis.constant.Constant.COMMA
import com.example.springbootbasis.constant.Constant.SERVICES
import com.example.springbootbasis.constant.Constant.Service.createBeanName
import com.example.springbootbasis.service.MyDetailService
import com.example.springbootbasis.service.MyService
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CustomApplicationRunner(
    private val context: ApplicationContext
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val serviceArgs = args?.getOptionValues(SERVICES) ?: throw RuntimeException("아규먼트가 없음")
        val services = serviceArgs.first().split(COMMA)

        services.forEach { service ->
            // bean name + type
            val currentBean = context.getBean(createBeanName(service), MyService::class.java)
            log.info("bean :: $currentBean")
        }

//        println(context.getBean("appleKService"))
//        println(context.getBean("appleKService"))
//        println(context.getBean("appleKService"))
//        println(context.getBean("bananaKService"))
//        println(context.getBean("bananaKService"))
//        println(context.getBean("bananaKService"))
//
//        val appleService = context.getBean("appleKService") as MyService
//        appleService.doSomething()
//
//        val bananaService = context.getBean("bananaKService") as MyService
//        bananaService.doSomething()
    }
}