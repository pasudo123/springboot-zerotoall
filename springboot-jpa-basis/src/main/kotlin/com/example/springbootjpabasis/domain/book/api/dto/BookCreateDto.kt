package com.example.springbootjpabasis.domain.book.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.SchemaProperty

@Schema(title = "BookCreateDto")
data class BookCreateDto(
    @SchemaProperty(name = "책 이름")
    val name: String,
    @SchemaProperty(name = "작가")
    val author: String,
    @SchemaProperty(name = "출판사")
    val publisher: String,
    @SchemaProperty(name = "서점 아이디")
    val libraryId: Long
)
