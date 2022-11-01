package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.data.redis.core.RedisOperations

interface StringRedisOperation : RedisOperations<String, String>
