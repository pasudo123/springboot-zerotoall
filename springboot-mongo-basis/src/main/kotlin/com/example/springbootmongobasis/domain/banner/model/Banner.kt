package com.example.springbootmongobasis.domain.banner.model

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.domain.banner.api.dto.BannerDto
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "banner")
class Banner private constructor(
    val name: String,
    val desc: String
): BaseDocument() {

    companion object {
        fun from(bannerDto: BannerDto.CreateRequest): Banner {
            return bannerDto.run {
                Banner(
                    this.name,
                    this.desc
                )
            }
        }
    }
}