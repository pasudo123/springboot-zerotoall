package com.example.springbootmongobasis.domain.user

data class UserResponses(
    val users: List<User>,
    val totalPages: Int,
    val totalElements: Long
)
