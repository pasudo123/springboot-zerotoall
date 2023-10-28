package com.example.springdocumenttraining.chapter01

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext
import org.springframework.stereotype.Component

@Component
class Chapter01Runner : ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        ClassPathXmlApplicationContext("xmlbeans/sample.xml").check()
        GenericGroovyApplicationContext("groovybeans/sample.groovy").check()
    }

    private fun ClassPathXmlApplicationContext.check() {
        val context = this
        with(context) {
            println("beanDefinitionCount=${this.beanDefinitionCount}")
            println("beanDefinitionNames=${this.beanDefinitionNames.toList()}")
            val coffeeServiceBean = this.getBean("coffeeService") as CoffeeService
            println("coffServiceBean=${coffeeServiceBean.javaClass}")
            val coffeeRepositoryBean = this.getBean("coffeeRepository") as CoffeeRepository
            println("coffeeRepositoryBean=${coffeeRepositoryBean.javaClass}")
        }
    }

    private fun GenericGroovyApplicationContext.check() {
        val context = this
        with(context) {
            println("beanDefinitionCount=${this.beanDefinitionCount}")
            println("beanDefinitionNames=${this.beanDefinitionNames.toList()}")
            val coffeeServiceBean = this.getBean(CoffeeService::class.java)
            val coffeeRepositoryBean = this.getBean(CoffeeRepository::class.java)
            println("coffServiceBean=${coffeeServiceBean.javaClass}")
            println("coffeeRepositoryBean=${coffeeRepositoryBean.javaClass}")
        }
    }
}
