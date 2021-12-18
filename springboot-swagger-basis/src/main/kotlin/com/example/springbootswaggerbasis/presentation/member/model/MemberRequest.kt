package com.example.springbootswaggerbasis.presentation.member.model

class MemberRequest {

    data class Create(
        val name: String,
        val age: Int,
    )

    data class Patch(
        val name: String?,
        val age: Int?
    )
}