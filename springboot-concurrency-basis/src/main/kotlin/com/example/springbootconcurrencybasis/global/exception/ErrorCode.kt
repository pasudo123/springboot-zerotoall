package com.example.springbootconcurrencybasis.global.exception

enum class ErrorCode(val message: String) {

    // 엔티티 조회 익셉션
    E100("찾을 수 없습니다."),

    // 시스템 정책 익셉션
    SP100("티켓 예약을 더 이상 할 수 없습니다."),

    // 시스템 런타임 익셉션
    SR100(""),
}