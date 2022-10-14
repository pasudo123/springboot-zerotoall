package com.example.springbootreactivebasis.exercise

import reactor.core.publisher.Flux

/**
 * springboot 와 별개로 실행되는 프로젝트
 */
fun main() {
    val elements = mutableListOf<Int>()

    val just = Flux.just(1, 2, 3, 4)
    just
        .log()
        .subscribe(elements::add)

    println(elements)
}
