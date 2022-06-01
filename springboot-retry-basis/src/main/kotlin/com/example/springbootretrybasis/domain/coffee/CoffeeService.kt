package com.example.springbootretrybasis.domain.coffee

import com.example.springbootretrybasis.config.DefaultListenerSupport
import com.example.springbootretrybasis.exception.CoffeeInsertException
import org.slf4j.LoggerFactory
import org.springframework.retry.RetryCallback
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CoffeeService(
    private val retryTemplate: RetryTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable
    fun insertCoffeeNoRecover(message: String) {
        val num = Random.nextLong(10)
        if (num < 9) {
            log.error("force error !!")
            throw RuntimeException("$num 으로 인한 에러 발생")
        }

        log.info("success")
    }

    @Retryable(listeners = ["defaultListenerSupport"])
    @Throws(exceptionClasses = [CoffeeInsertException::class])
    fun insertCoffeeWithRecover(
        firstMessage: String,
        secondMessage: String? = null
    ) {
        val num = Random.nextLong(10)
        if (num < 9) {
            log.error("force error !!")
            throw CoffeeInsertException("$num 으로 인한 에러 발생")
        }

        log.info("success")
    }

    @Recover
    private fun recoverByInsertCoffee(
        exception: CoffeeInsertException,
        firstMessage: String,
        secondMessage: String? = null

    ) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("[retry recover]")
            this.appendLine("firstMessage : $firstMessage")
            this.appendLine("secondMessage : $secondMessage")
        }

        log.info(lines.toString())
    }

    fun insertCoffeeWithRetryTemplate() {
        retryTemplate.execute(RetryCallback<Void, CoffeeInsertException> { context ->
            this.insertCoffee()
            null
        })
    }

    private fun insertCoffee() {

        log.info("insertCoffee() called...")

        val num = Random.nextLong(10)
        if (num < 9) {
            log.error("force error !!")
            throw CoffeeInsertException("$num 으로 인한 에러 발생")
        }
    }
}