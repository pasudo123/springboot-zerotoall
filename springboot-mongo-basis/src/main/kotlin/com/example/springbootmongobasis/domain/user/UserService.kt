package com.example.springbootmongobasis.domain.user

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findAllWithPage(page: Int, size: Int): UserResponses {

        // page 는 1부터 받도록 한다.
        val pageable = PageRequest.of((page - 1), size, Direction.ASC, "id")
        val pageResponse = userRepository.findAll(pageable)

        return UserResponses(
            users = pageResponse.content,
            totalPages = pageResponse.totalPages,
            totalElements = pageResponse.totalElements
        )
    }
}
