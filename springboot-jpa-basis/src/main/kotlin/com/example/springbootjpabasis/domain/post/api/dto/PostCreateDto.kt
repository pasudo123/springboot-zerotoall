package com.example.springbootjpabasis.domain.post.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.SchemaProperty

@Schema(name = "PostCreateDto")
class PostCreateDto(
    @SchemaProperty(name = "게시글 내용")
    val contents: String
)
