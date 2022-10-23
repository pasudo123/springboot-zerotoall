package com.example.springbootmongobasis

import org.slf4j.LoggerFactory
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer

class MongoInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        val addedProperties = listOf(
            "spring.data.mongodb.authentication-database=admin",
            "spring.data.mongodb.auto-index-creation=true",
            "spring.data.mongodb.uri=${MongoContainerSingleton.instance.replicaSetUrl}",
        )
        TestPropertyValues.of(addedProperties).applyTo(applicationContext.environment)
    }
}

object MongoContainerSingleton {

    private val log = LoggerFactory.getLogger(javaClass)
    val instance: MongoDBContainer by lazy { startMongoContainer() }

    private fun startMongoContainer(): MongoDBContainer {

        log.info(
            "\nos.name ==> ${System.getProperty("os.name")}\n" +
                "os.arch ==> ${System.getProperty("os.arch")}\n" +
                "os.version ==> ${System.getProperty("os.arch")}"
        )

        return MongoDBContainer("mongo:4.4.2")
            .withReuse(true)
            .apply {
                this.start()
            }
    }
}
