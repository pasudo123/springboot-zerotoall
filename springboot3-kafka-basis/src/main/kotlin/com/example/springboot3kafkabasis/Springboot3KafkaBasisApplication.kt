package com.example.springboot3kafkabasis

import com.example.springboot3kafkabasis.Springboot3KafkaBasisApplication.Companion.mapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@Configuration
@EnableKafka
// @EnableKafkaStreams
// @EnableKafkaRetryTopic
class Springboot3KafkaBasisApplication {

    companion object {
        val mapper = ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(JavaTimeModule())
            .registerKotlinModule()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return mapper
    }
}

inline fun <reified T : Any> T.toJson(): String = mapper.writeValueAsString(this)

fun main(args: Array<String>) {
    runApplication<Springboot3KafkaBasisApplication>(*args)
}


