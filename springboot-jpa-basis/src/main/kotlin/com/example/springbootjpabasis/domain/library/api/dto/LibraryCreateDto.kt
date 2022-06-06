package com.example.springbootjpabasis.domain.library.api.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("LibraryCreateDto")
data class LibraryCreateDto(
    @ApiModelProperty("서점이름", example = "교보문고")
    val name: String,
    @ApiModelProperty("주소", example = "서울특별시 종로구 종로1가 종로 1")
    val address: String
)
