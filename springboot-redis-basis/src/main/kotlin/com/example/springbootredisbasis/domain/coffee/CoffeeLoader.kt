package com.example.springbootredisbasis.domain.coffee

import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import javax.annotation.PostConstruct

@Profile(value = ["default", "dev"])
@Component
class CoffeeLoader(
    private val factory: ReactiveRedisConnectionFactory,
    private val template: ReactiveRedisTemplate<String, Coffee>,
) {

    // https://spring.io/blog/2019/04/12/going-reactive-with-spring-coroutines-and-kotlin-flow
    @PostConstruct
    fun loadData() {
        factory.reactiveConnection.serverCommands().flushAll().thenMany(
            Flux.just("americano", "cappuccino", "cafe latte", "lemonade")
                .map { name -> Coffee(name) }
                .flatMap { coffee -> template.opsForValue().set(coffee.id, coffee) }
                .thenMany(
                    template.keys("*").flatMap { key -> template.opsForValue().get(key) }
                )
        )
    }
}