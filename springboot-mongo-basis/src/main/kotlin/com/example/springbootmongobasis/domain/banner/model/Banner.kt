package com.example.springbootmongobasis.domain.banner.model

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.domain.banner.api.dto.BannerDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.HashIndexed
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "banner")
class Banner private constructor(
    @Indexed
    val name: String,
    @HashIndexed
    val hashName: String,
    val desc: String
): BaseDocument() {

    @Id
    private var id: String? = null

    companion object {
        fun from(bannerDto: BannerDto.CreateRequest): Banner {
            return bannerDto.run {
                Banner(
                    this.name,
                    "hash - ${this.name}",
                    this.desc
                )
            }
        }
    }
}