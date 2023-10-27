package com.example.springboot3kafkabasis.kafka.consumer

import com.example.springboot3kafkabasis.exception.CoffeeIgnoreException
import com.example.springboot3kafkabasis.exception.CoffeeRecordException
import com.example.springboot3kafkabasis.kafka.KafkaConstant
import com.example.springboot3kafkabasis.model.Coffee
import com.example.springboot3kafkabasis.toObject
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CoffeeConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = [KafkaConstant.COFFEE_TOPIC],
        groupId = "coffee-consumer-group-1",
        concurrency = "1",
        errorHandler = "coffeeConsumeErrorHandler"
    )
    fun consume(message: String) {
        val coffee: Coffee = message.toObject()
        if (coffee.price <= 30000) {
            throw CoffeeRecordException("[consume] coffee-RecordException, coffee=$message")
        }

        if (coffee.price <= 50000) {
            throw CoffeeIgnoreException("[consume] Coffee-IgnoreException, coffee=$message")
        }

        log.info("[consume] message=$message")
    }
}
