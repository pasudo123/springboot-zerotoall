package com.example.springdocumenttraining.chapter03

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

class AnnotationBasedFineTuningSample01

@Configuration
class MyCustomConfiguration {

    @Bean
    fun actionBookCatalog(): BookCatalog {
        return BookCatalog("action")
    }

    @Bean
    fun comedyBookCatalog(): BookCatalog {
        return BookCatalog("comedy")
    }
}

class BookCatalog(
    val name: String
)

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier("comedyBookCatalog")
annotation class Comedy

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier("actionBookCatalog")
annotation class Action

@Component
class BookRecommender(
    @Action
    private val bookCatalog: BookCatalog
) {

    @PostConstruct
    fun init() {
        // println("catalog=${bookCatalog.name}")
    }
}