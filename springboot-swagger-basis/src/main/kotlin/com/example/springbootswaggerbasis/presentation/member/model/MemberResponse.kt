package com.example.springbootswaggerbasis.presentation.member.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class MemberResponse(
    val id: Long,
    val name: String,
    val age: Int,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val modifiedAt: LocalDateTime,
)