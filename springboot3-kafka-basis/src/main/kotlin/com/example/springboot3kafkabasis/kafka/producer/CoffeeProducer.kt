package com.example.springboot3kafkabasis.kafka.producer

import com.example.springboot3kafkabasis.model.Coffee
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CoffeeProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun produceCoffee(coffee: Coffee) {
        val future = kafkaTemplate.send(COFFEE_TOPIC, coffee)
        future.whenComplete { result, exception ->
            if (exception == null) {
              log.info("offset=${result.recordMetadata.offset()}, coffee.name=${coffee.name}, coffee.price=${coffee.price}")
            }
        }
    }

    companion object {
        const val COFFEE_TOPIC = "coffee-topic-1"
    }
}