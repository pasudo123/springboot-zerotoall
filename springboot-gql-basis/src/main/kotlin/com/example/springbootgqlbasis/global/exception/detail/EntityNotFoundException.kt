package com.example.springbootgqlbasis.global.exception.detail

import com.example.springbootgqlbasis.global.exception.ErrorCode

class EntityNotFoundException(
    val code: ErrorCode,
    override val message: String
) : RuntimeException(message)