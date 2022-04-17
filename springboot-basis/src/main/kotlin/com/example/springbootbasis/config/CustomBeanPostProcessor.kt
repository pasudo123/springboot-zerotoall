package com.example.springbootbasis.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class CustomBeanPostProcessor : BeanPostProcessor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (checkBeanName(beanName)) {
            log.info("===> before init : $bean")
        }
        return super.postProcessBeforeInitialization(bean, beanName)
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (checkBeanName(beanName)) {
            log.info("===> after init : $bean")
        }
        return super.postProcessAfterInitialization(bean, beanName)
    }

    private fun checkBeanName(beanName: String): Boolean {
        return (beanName == "springbootBasisApplication" || beanName.contains("KService"))
    }
}