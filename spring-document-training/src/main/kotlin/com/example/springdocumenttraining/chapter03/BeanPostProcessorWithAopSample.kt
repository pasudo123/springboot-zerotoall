package com.example.springdocumenttraining.chapter03

import com.example.springdocumenttraining.chapter03.BeanPostProcessorWithAopSample.Companion.log
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BeanPostProcessorWithAopSample {
    companion object {
        val log = LoggerFactory.getLogger(this::class.java)
    }
}


@Configuration
class CustomBeanPostProcessorConfiguration {

    @Bean
    fun water(): Water {
        return Water()
    }

    @Bean
    fun myCustomBeanPostProcessor(): BeanPostProcessor {
        return MyCustomBeanPostProcessor()
    }
}

class Water {
    @PostConstruct
    fun init() {
        // log.info("@@ water postConstruct")
    }
}

class MyCustomBeanPostProcessor: BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is Water) {
            // log.info("before init bean, bean.name=${beanName}")
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is Water) {
            // log.info("after init bean, bean.name=${beanName}")
        }
        return bean
    }
}