package com.example.springdocumenttraining.chapter03

import com.example.springdocumenttraining.chapter03.BeanPostProcessorWithAnnotationSample.Companion.log
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

class BeanPostProcessorWithAnnotationSample {
    companion object {
        val log = LoggerFactory.getLogger(this::class.java)
    }
}

//@Component
class Apple

@Component
class Banana {
//    @Autowired
//    lateinit var apple: Apple
}

@Configuration
class CustomAutowiredBeanPostProcessorConfiguration {

    @Bean
    fun apple(): Apple {
        return Apple()
    }

    @Bean
    fun myAnnotationBeanPostProcessor(): MyAnnotationBeanPostProcessor {
        return MyAnnotationBeanPostProcessor()
    }
}

class MyAnnotationBeanPostProcessor: AutowiredAnnotationBeanPostProcessor() {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is Apple) {
            log.info("== before init bean, bean.name=${beanName}")
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is Apple) {
            log.info("== after init bean, bean.name=${beanName}")
        }
        return bean
    }
}