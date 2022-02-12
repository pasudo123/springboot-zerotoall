package com.example.springbootquartzbasis.dev.api

import com.example.springbootquartzbasis.dev.service.ErrorJobService
import com.example.springbootquartzbasis.dev.service.WriteJobService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quartz")
class DevQuartzController(
    private val writeJobService: WriteJobService,
    private val errorJobService: ErrorJobService,
) {

    @PostMapping("write")
    fun writeJobCreate(
        @RequestParam("size") size: Int
    ) {
        // 하나의 잡에 여러개 트리거
        // 트리거 하는 순간에는 디비에 남지만, 이후에는 디비에서 자동으로 삭제처리
        writeJobService.process(size)
    }

    @PostMapping("write-async")
    fun writeAsyncJobCreate(
        @RequestParam("size") size: Int
    ) {
        // 하나의 잡에 여러개 트리거 : coroutine 포함, 다만 suspend 를 안붙인 상태이고 trigger 는 10초뒤에 시작한다는 개념으로 출발함.
        // 트리거 하는 순간에는 디비에 남지만, 이후에는 디비에서 자동으로 삭제처리
        writeJobService.processWithAsync(size)
    }

    @PostMapping("job-execute-error")
    fun jobExecuteError() {

    }
}