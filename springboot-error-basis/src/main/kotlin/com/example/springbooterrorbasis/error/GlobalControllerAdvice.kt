package com.example.springbooterrorbasis.error

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalControllerAdvice {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [AException::class])
    fun handleAException(
        aException: AException, request: WebRequest
    ): ResponseEntity<ErrorDetail> {
        val errorDetail = aException.errorDetail.apply {
            this.requestUri = request.toRequestUri()
            this.requestMethod = request.toRequestMethod()
        }

        return ResponseEntity
            .status(aException.errorDetail.httpStatus)
            .body(errorDetail)
    }

    private fun WebRequest.toRequestUri(): String = (this as ServletWebRequest).request.requestURI.toString()
    private fun WebRequest.toRequestMethod(): String = (this as ServletWebRequest).request.method
}