package com.example.springbootconcurrencybasis.global

import com.example.springbootconcurrencybasis.global.exception.ErrorMessage
import com.example.springbootconcurrencybasis.global.exception.detail.EntityNotFoundException
import com.example.springbootconcurrencybasis.global.exception.detail.SystemPolicyException
import com.example.springbootconcurrencybasis.global.exception.detail.SystemRuntimeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleEntityNotFoundException(
        e: EntityNotFoundException, request: WebRequest
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(e.code.name, e.message, request.toRequestURL())
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorMessage)
    }

    @ExceptionHandler(value = [SystemPolicyException::class])
    fun handleSystemPolicyException(
        e: SystemPolicyException, request: WebRequest
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(e.code.name, e.message, request.toRequestURL())
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorMessage)
    }

    @ExceptionHandler(value = [SystemRuntimeException::class])
    fun handleSystemRuntimeException(
        e: SystemRuntimeException, request: WebRequest
    ): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(e.code.name, e.message, request.toRequestURL())
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorMessage)
    }
}

fun WebRequest.toRequestURL(): String =
    (this as ServletWebRequest).request.requestURL.toString()