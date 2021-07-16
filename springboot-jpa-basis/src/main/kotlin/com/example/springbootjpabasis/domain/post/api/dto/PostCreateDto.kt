package com.example.springbootjpabasis.domain.post.api.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("PostCreateDto")
class PostCreateDto(
    @ApiModelProperty("게시글 내용")
    val contents: String
)