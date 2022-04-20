package com.example.springbootbasis.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HolderAnnotation(
    val operation: String
)
