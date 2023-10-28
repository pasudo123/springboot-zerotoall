package com.example.springdocumenttraining

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@SpringBootApplication
class SpringDocumentTrainingApplication

fun main(args: Array<String>) {
    runApplication<SpringDocumentTrainingApplication>(*args)
}

@RequestMapping("/index")
@RestController
class IndexController {

    @GetMapping
    fun index(): String {
        return """
            {
                "index": "index",
                "currentDateTime": "${LocalDateTime.now()}"
            }
        """.trimIndent()
    }
}
