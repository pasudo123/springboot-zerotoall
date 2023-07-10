package com.example.springbootbasis.config

import com.example.springbootbasis.constant.Constant.COMMA
import com.example.springbootbasis.constant.Constant.SERVICES
import com.example.springbootbasis.constant.Constant.Service.createBeanName
import com.example.springbootbasis.scheduler.MyScheduler
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class CustomApplicationRunner(
    private val context: ApplicationContext,
    private val env: Environment
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {

        if (isNoArgsScheduleRun(env)) {
            log.info("does not create schedule bean !!")
            return
        }

        // --services=apple
        val serviceArgs = args?.getOptionValues(SERVICES) ?: throw RuntimeException("아규먼트가 없음")
        val services = serviceArgs.first().split(COMMA)

        // 동적으로 빈을 만든상태로 불러오는 형태.
        services.forEach { service ->
            // bean name + type : getBean 을 통해서 내부적으로 빈을 생성할 수 있음
            val currentBean = context.getBean(createBeanName(service), MyScheduler::class.java)
            log.info("bean :: $currentBean")
        }
    }

    private fun isNoArgsScheduleRun(env: Environment): Boolean {
        return env.getProperty("app.scheduler.use.args")?.toBooleanStrictOrNull()?.not() ?: true
    }
}