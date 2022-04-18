package com.example.springbootbasis.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
class CustomBeanFactoryPostProcessor : BeanFactoryPostProcessor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("===========================================")
            this.appendLine("called post processor bean factory...")
            this.appendLine("===========================================")
        }

        for (beanName in beanFactory.beanDefinitionNames) {
            val beanDefinition = beanFactory.getBeanDefinition(beanName!!)
            // 여기에서 빈에 대한 조작을 수행할 수 있다.
        }
        
        log.info(lines.toString())
    }
}