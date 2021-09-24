package com.example.springbootgqlbasis.global.exception.detail

import com.example.springbootgqlbasis.global.exception.ErrorCode

class DomainEntityNotFoundException(
    val code: ErrorCode,
    override val message: String
) : RuntimeException(message)