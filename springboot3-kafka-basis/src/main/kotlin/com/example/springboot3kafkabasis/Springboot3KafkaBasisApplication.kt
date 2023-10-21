package com.example.springboot3kafkabasis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaRetryTopic
import org.springframework.kafka.annotation.EnableKafkaStreams

@SpringBootApplication
@EnableKafka
// @EnableKafkaStreams
// @EnableKafkaRetryTopic
class Springboot3KafkaBasisApplication

fun main(args: Array<String>) {
    runApplication<Springboot3KafkaBasisApplication>(*args)
}


