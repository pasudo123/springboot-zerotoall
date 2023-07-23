package com.example.springbootbasis.exception

import org.slf4j.LoggerFactory
import org.springframework.util.ErrorHandler
import java.util.concurrent.RejectedExecutionException

class CustomScheduleErrorHandler : ErrorHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun handleError(throwable: Throwable) {

        if (throwable is CustomException) {
            log.error("[customException] error handler called !! : ${throwable.message}")
            return
        }

        if (throwable is RejectedExecutionException) {
            val errorMessage = """
                {
                    "cause.message": "${throwable.cause?.message}",
                }
            """.trimIndent()
            log.error("[RejectedExecutionException] error handler called !! :\n$errorMessage")
            return
        }

        log.error("[runtimeException] error handler called !! : ${throwable.message}")
    }
}