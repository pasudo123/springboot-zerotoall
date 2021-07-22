package com.example.springbootmongobasis

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer

class MongoInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        val addedProperties = listOf(
            "spring.data.mongodb.uri=${MongoContainerSingleton.instance.replicaSetUrl}"
        )
        TestPropertyValues.of(addedProperties).applyTo(applicationContext.environment)
    }
}

object MongoContainerSingleton {

    val instance: MongoDBContainer by lazy { startMongoContainer() }

    private fun startMongoContainer(): MongoDBContainer {
        return MongoDBContainer("mongo:4.4.2")
            .withReuse(true)
            .apply {
                this.start()
            }
    }
}