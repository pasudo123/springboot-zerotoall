package com.example.springbootgqlbasis.global

import com.example.springbootgqlbasis.global.exception.ErrorMessage
import com.example.springbootgqlbasis.global.exception.detail.EntityNotFoundException
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
}

fun WebRequest.toRequestURL(): String =
    (this as ServletWebRequest).request.requestURL.toString()