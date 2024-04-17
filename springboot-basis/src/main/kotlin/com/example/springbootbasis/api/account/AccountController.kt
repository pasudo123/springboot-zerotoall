package com.example.springbootbasis.api.account

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("accounts")
class AccountController {

    @GetMapping("random")
    fun getAccount(): Account {
        return Account("홍길동-${Random.nextLong(until = 100)}")
    }
}

data class Account(
    val name: String
)
