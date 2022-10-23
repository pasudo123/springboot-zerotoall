package com.example.springbootmongobasis.domain.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun findAllByPage(
        @RequestParam("page", required = true) page: Int,
        @RequestParam("size") size: Int
    ): UserResponses {

        if (page <= 0) {
            throw RuntimeException("page <= 0 이 될 수 없습니다.")
        }

        return userService.findAllWithPage(page, size)
    }
}
