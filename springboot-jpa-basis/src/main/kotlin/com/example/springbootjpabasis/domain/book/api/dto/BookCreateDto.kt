package com.example.springbootjpabasis.domain.book.api.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("BookCreateDto")
data class BookCreateDto(
    @ApiModelProperty("책 이름", example = "고래")
    val name: String,
    @ApiModelProperty("작가", example = "천명관")
    val author: String,
    @ApiModelProperty("출판사", example = "문학동네")
    val publisher: String,
    @ApiModelProperty("서점 아이디")
    val libraryId: Long
)
