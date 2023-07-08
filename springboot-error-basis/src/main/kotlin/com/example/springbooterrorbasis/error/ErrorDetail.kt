package com.example.springbooterrorbasis.error

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

data class ErrorDetail(
    val errorCode: Long,
    val message: String,
    @JsonIgnore
    val httpStatus: HttpStatus,
    val cause: Map<String, Any> = emptyMap()
) {

    var requestUri: String? = null
    var requestMethod: String? = null
}