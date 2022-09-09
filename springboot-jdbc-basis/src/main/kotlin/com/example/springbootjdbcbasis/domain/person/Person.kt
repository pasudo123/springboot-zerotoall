package com.example.springbootjdbcbasis.domain.person

data class Person(
    val name: String,
    val email: String,
    var remark: String? = null
)