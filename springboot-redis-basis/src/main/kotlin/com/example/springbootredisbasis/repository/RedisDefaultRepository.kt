package com.example.springbootredisbasis.repository

import java.util.concurrent.TimeUnit

interface RedisDefaultRepository {

    data class RedisTTL(
        val long: Long,
        val unit: TimeUnit
    )
}
