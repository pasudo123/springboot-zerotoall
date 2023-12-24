package com.example.springbootjpabasis.domain.library.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.SchemaProperty

@Schema(name = "LibraryCreateDto")
data class LibraryCreateDto(
    @SchemaProperty(name = "서점이름")
    val name: String,
    @SchemaProperty(name = "주소")
    val address: String
)
