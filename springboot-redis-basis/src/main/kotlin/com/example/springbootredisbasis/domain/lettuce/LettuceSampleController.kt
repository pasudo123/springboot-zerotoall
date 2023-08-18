package com.example.springbootredisbasis.domain.lettuce

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("custom-lettuce")
class LettuceSampleController(
    private val customLettuceService: CustomLettuceService
) {

    @PostMapping
    fun doSomething() {
        this.customLettuceService.doSomething()
    }
}