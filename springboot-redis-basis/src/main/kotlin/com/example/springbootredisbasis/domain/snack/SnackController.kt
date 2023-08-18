package com.example.springbootredisbasis.domain.snack

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("snacks")
class SnackController(
    private val snackRedisRepository: SnackRedisRepository
)
