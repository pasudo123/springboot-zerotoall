package com.example.springboot3kafkabasis.kafka.producer

import com.example.springboot3kafkabasis.kafka.KafkaConstant.COFFEE_TOPIC
import com.example.springboot3kafkabasis.model.Coffee
import com.example.springboot3kafkabasis.toJson
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CoffeeProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun produceCoffee(coffee: Coffee) {
        val future = kafkaTemplate.send(COFFEE_TOPIC, coffee.key, coffee.toJson())
        future.whenComplete { result, exception ->
            if (exception == null) {
                log.info("[send] offset=${result.recordMetadata.offset()}, coffee.name=${coffee.name}, coffee.price=${coffee.price}")
                return@whenComplete
            }
            log.error("[send] exception.message=${exception.message}")
        }
    }
}