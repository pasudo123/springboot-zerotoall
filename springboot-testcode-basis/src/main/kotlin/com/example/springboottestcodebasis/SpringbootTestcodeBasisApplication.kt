package com.example.springboottestcodebasis

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import kotlin.random.Random

@SpringBootApplication
class SpringbootTestcodeBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootTestcodeBasisApplication>(*args)
}

@RestController
@RequestMapping("short-news-mock")
class ShortNewsMockController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("sleep")
    fun getWithSleep(): String {

        logger.info("call : {}", LocalDateTime.now())
        Thread.sleep(Random.nextLong(1000, 2000))

        return """
            {
                "category": "test",
                "data": [
                    {
                        "author": "test-author-1"
                    },
                    {
                        "author": "test-author-2"
                    }
                ],
                "success": "OK"
            }
        """.trimIndent()
    }
}
