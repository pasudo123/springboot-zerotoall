package com.example.springboot3kafkabasis.kafka.consumer

import com.example.springboot3kafkabasis.exception.CoffeeIgnoreException
import com.example.springboot3kafkabasis.exception.CoffeeRecordException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.listener.KafkaListenerErrorHandler

@Configuration
class CoffeeConsumerConfiguration {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun coffeeConsumeErrorHandler(): KafkaListenerErrorHandler {
        return KafkaListenerErrorHandler { message, exception ->
            when (exception.cause) {
                is CoffeeRecordException -> {
                    log.error("**CoffeeRecordException** : ${exception.cause?.message}")
                }
                is CoffeeIgnoreException -> {
                    log.error("**CoffeeIgnoreException** : ${exception.cause?.message}")
                }
                else -> {
                    log.error("수행되지 않음 : 에러가 발생안했기 때문에")
                }
            }
            message
        }
    }
}