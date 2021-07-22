package com.example.springbootmongobasis.domain.banner.api

import com.example.springbootmongobasis.domain.banner.api.dto.BannerDto
import com.example.springbootmongobasis.domain.banner.model.Banner
import com.example.springbootmongobasis.domain.banner.repository.BannerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("banners")
class BannerController(
    private val bannerRepository: BannerRepository
) {

    @PostMapping
    fun create(
        @RequestBody request: BannerDto.CreateRequest
    ): ResponseEntity<Banner> {
        val banner = bannerRepository.save(Banner.from(request))
        return ResponseEntity.ok(banner)
    }
}