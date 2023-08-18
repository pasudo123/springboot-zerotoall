// package com.example.springbootredisbasis.domain.coffee
//
// import org.springframework.data.redis.core.ReactiveRedisTemplate
// import org.springframework.web.bind.annotation.GetMapping
// import org.springframework.web.bind.annotation.RequestMapping
// import org.springframework.web.bind.annotation.RestController
// import reactor.core.publisher.Flux
//
// @RestController
// @RequestMapping("coffees")
// class CoffeeController(
//    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Coffee>
// ) {
//
//    @GetMapping
//    fun all(): Flux<Coffee> {
//        return reactiveRedisTemplate.keys("*")
//            .flatMap { key -> reactiveRedisTemplate.opsForValue().get(key) }
//    }
// }
