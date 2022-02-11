package com.example.springbootquartzbasis.dev.api

import com.example.springbootquartzbasis.dev.service.WriteJobService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quartz")
class DevQuartzController(
    private val writeJobService: WriteJobService,
) {

    /**
     * 여러 개 만들때, 다 동작하는지 체크
     */
    @PostMapping("write")
    fun writeJobCreate(
        @RequestParam("size") size: Int
    ) {
        writeJobService.process(size)
    }
}