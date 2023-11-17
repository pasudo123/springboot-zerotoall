// package com.example.springdocumenttraining.chapter01
//
// import org.slf4j.LoggerFactory
// import org.springframework.boot.ApplicationArguments
// import org.springframework.boot.ApplicationRunner
// import org.springframework.context.ApplicationContext
// import org.springframework.context.support.ClassPathXmlApplicationContext
// import org.springframework.context.support.GenericGroovyApplicationContext
// import org.springframework.stereotype.Component
//
// @Component
// class Chapter01Runner(
//    private val applicationContext: ApplicationContext
// ) : ApplicationRunner {
//
//    private val log = LoggerFactory.getLogger(javaClass)
//
//    override fun run(args: ApplicationArguments?) {
//        ClassPathXmlApplicationContext("xmlbeans/sample.xml").check(true)
//        log.info("----------")
//        GenericGroovyApplicationContext("groovybeans/sample.groovy").check(false)
//        log.info("----------")
//        applicationContext.check(true)
//    }
//
//    private fun ClassPathXmlApplicationContext.check(logging: Boolean = true) {
//        if (logging.not()) {
//            return
//        }
//
//        val context = this
//        with(context) {
//            log.info("beanDefinitionCount=${this.beanDefinitionCount}")
//            log.info("beanDefinitionNames=${this.beanDefinitionNames.toList()}")
//            val coffeeServiceBean = this.getBean(COFFEE_SERVICE) as CoffeeService
//            val coffeeRepositoryBean = this.getBean(COFFEE_REPOSITORY) as CoffeeRepository
//            val coffeeFindServiceBean = this.getBean("coffeeFindServiceCustom") as CoffeeFindService
//            log.info("coffServiceBean=${coffeeServiceBean.javaClass}, coffServiceBean.hashcode=${coffeeServiceBean.hashCode()}")
//            log.info("coffeeRepositoryBean=${coffeeRepositoryBean.javaClass}")
//            log.info("coffeeFindServiceBean=${coffeeFindServiceBean.javaClass}")
//        }
//
//        with(context.beanFactory.getBeanDefinition(COFFEE_FIND_SERVICE)) {
//            log.info("coffeeService.beanClassName=${this.beanClassName}")
//            log.info("coffeeService.factoryBeanName=${this.factoryBeanName}")
//            log.info("coffeeService.description=${this.description}")
//            log.info("coffeeService.scope=${this.scope}")
//            log.info("coffeeService.resolvableType=${this.resolvableType}")
//            log.info("coffeeService.singleton=${this.isSingleton}")
//        }
//    }
//
//    private fun GenericGroovyApplicationContext.check(logging: Boolean = true) {
//        if (logging.not()) {
//            return
//        }
//
//        val context = this
//        with(context) {
//            log.info("beanDefinitionCount=${this.beanDefinitionCount}")
//            log.info("beanDefinitionNames=${this.beanDefinitionNames.toList()}")
//            val coffeeServiceBean = this.getBean(CoffeeService::class.java)
//            val coffeeRepositoryBean = this.getBean(CoffeeRepository::class.java)
//            log.info("coffServiceBean=${coffeeServiceBean.javaClass}, coffServiceBean.hashcode=${coffeeServiceBean.hashCode()}")
//            log.info("coffeeRepositoryBean=${coffeeRepositoryBean.javaClass}")
//        }
//    }
//
//    private fun ApplicationContext.check(logging: Boolean = true) {
//        if (logging.not()) {
//            return
//        }
//
//        val context = this
//        with(context) {
//            val coffeeControllerBean = this.getBean(CoffeeController::class.java)
//            log.info("coffeeControllerBean=${coffeeControllerBean.javaClass}")
//        }
//    }
//
//    companion object {
//        private const val COFFEE_SERVICE = "coffeeService"
//        private const val COFFEE_FIND_SERVICE = "coffeeFindService"
//        private const val COFFEE_REPOSITORY = "coffeeRepository"
//    }
// }
