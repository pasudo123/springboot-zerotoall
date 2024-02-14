package com.example.springbootjpabasis.config.async

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

class BasisAsyncErrorHandler: AsyncUncaughtExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun handleUncaughtException(ex: Throwable, method: Method, vararg params: Any?) {
        log.error("[asyncException] ${ex.localizedMessage}, params=${params}")
    }
}