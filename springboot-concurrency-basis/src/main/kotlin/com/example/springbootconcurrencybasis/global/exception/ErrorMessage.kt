package com.example.springbootconcurrencybasis.global.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ErrorMessage(
    val code: String,
    val message: String,
    val requestURL: String
) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
    val time: LocalDateTime = LocalDateTime.now()
}