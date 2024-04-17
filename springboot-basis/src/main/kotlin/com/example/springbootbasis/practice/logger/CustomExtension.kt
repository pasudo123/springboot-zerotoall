package com.example.springbootbasis.practice.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CustomExtension

val logger: Logger = LoggerFactory.getLogger(CustomExtension::class.java)

data class PersonTest1(
    val name: String,
    val age: Int,
    var mind: String? = null
)

fun PersonTest1.toBatMan(): PersonTest1 {
    logger.info("person -> batman")
    return this.copy(
        name = "batman",
        age = 99999
    )
}
