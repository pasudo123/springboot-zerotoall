package com.example.springbootretrofitbasis.api

import com.example.springbootretrofitbasis.client.shortnews.ShortNewsClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("dev/v3")
class DevVersion3Controller(
    private val shortNewsClient: ShortNewsClient
) {


}