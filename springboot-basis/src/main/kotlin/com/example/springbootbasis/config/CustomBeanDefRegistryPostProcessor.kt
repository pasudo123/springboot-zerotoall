package com.example.springbootbasis.config

import com.example.springbootbasis.constant.Constant
import com.example.springbootbasis.service.MyService
import org.slf4j.LoggerFactory
import org.springframework.beans.MutablePropertyValues
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.beans.factory.support.RootBeanDefinition
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class CustomBeanDefRegistryPostProcessor : BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private lateinit var env: Environment

    private val log = LoggerFactory.getLogger(javaClass)

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        this.logging("[beanDef] postProcessBeanFactory ...")
    }

    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {

        val services = env.getProperty(Constant.SERVICES)?.split(Constant.COMMA) ?: emptyList()
        this.logging(services.joinToString(", "))

        services.forEach { serviceName ->
            val propertyValues = MutablePropertyValues().apply {
                this.add("name", serviceName)
            }

            val beanDef = RootBeanDefinition(MyService::class.java).apply {
                this.targetType = MyService::class.java
                this.role = BeanDefinition.ROLE_APPLICATION
                this.propertyValues = propertyValues
            }

            registry.registerBeanDefinition("${serviceName}KService", beanDef)
        }



        this.logging("[beanDef] postProcessBeanDefinitionRegistry ...")
    }

    private fun logging(line: String) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("===========================================")
            this.appendLine(line)
            this.appendLine("===========================================")
        }

        log.info(lines.toString())
    }

    override fun setEnvironment(environment: Environment) {
        this.env = environment
    }
}