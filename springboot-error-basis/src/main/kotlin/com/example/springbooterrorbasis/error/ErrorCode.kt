package com.example.springbooterrorbasis.error

enum class ErrorCode(val code: Long, val desc: String) {
    A_EXCEPTION(30000, "A 익셉션 발생"),
    B_EXCEPTION(31000, "B 익셉션 발생"),
    C_EXCEPTION(32000, "C 익셉션 발생"),
    D_EXCEPTION(33000, "D 익셉션 발생"),
}