package com.example.springbootconcurrencybasis.global.exception.detail

import com.example.springbootconcurrencybasis.global.exception.ErrorCode

class SystemPolicyException(
    val code: ErrorCode,
    override val message: String
) : RuntimeException(message)
