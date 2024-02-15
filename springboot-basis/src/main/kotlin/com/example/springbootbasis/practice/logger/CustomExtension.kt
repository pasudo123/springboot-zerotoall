package com.example.springbootbasis.practice.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CustomExtension

val logger: Logger  = LoggerFactory.getLogger(CustomExtension::class.java)

fun Person.toBatMan(): Person {
    logger.info("person -> batman")
    return this.copy(
        name = "batman",
        age = 99999
    )
}