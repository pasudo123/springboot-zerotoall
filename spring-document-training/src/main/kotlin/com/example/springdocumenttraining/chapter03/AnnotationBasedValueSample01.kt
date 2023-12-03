package com.example.springdocumenttraining.chapter03

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

class AnnotationBasedValueSample01

@Configuration
class MyCustomConfiguration2th

@Component
@PropertySources(
    value = [
        PropertySource("classpath:application.properties"),
    ]
)
class MyConfigStorage(
    @Value("\${person.name: park}")
    val personName: String,
    @Value("\${person.age: 22}")
    val personAge: Int,
) {

    init {
        println("name=$personName, age=$personAge")
    }
}