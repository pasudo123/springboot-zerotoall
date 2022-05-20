package com.example.springbootbasis.exception

import org.slf4j.LoggerFactory
import org.springframework.util.ErrorHandler

class CustomScheduleErrorHandler : ErrorHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun handleError(throwable: Throwable) {

        if (throwable is CustomException) {
            log.info("[customException] error handler called !!")
            log.info(throwable.message)
            return
        }

        log.info("[runtimeException] error handler called !!")
        log.info(throwable.message)
    }
}