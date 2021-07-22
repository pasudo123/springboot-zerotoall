package com.example.springbootmongobasis.domain.banner.api.dto

class BannerDto {

    data class CreateRequest(
        val name: String,
        val desc: String
    )
}