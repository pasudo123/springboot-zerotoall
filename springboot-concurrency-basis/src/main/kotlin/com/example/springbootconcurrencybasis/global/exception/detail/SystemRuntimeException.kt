package com.example.springbootconcurrencybasis.global.exception.detail

import com.example.springbootconcurrencybasis.global.exception.ErrorCode

class SystemRuntimeException (
    val code: ErrorCode,
    override val message: String
) : RuntimeException(message)