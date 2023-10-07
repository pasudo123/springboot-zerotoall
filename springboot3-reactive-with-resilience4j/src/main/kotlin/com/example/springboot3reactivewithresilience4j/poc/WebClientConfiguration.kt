package com.example.springboot3reactivewithresilience4j.poc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfiguration {

    @Bean
    fun demoWebClient(): WebClient {
        val httpClient = HttpClient
            .create()
            .baseUrl("http://localhost:9090")

        return WebClient
            .builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }
}
