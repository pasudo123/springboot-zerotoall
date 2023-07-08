package com.example.springbooterrorbasis.sample.api

import com.example.springbooterrorbasis.error.AException
import com.example.springbooterrorbasis.error.ErrorCode
import com.example.springbooterrorbasis.error.ErrorDetail
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("force-error")
class ForceExceptionController {

    @GetMapping("a")
    fun errorA(
        @RequestParam("flag") flag: Boolean?
    ): String {

        if (flag == null || flag.not()) {
            return "OK"
        }

        val errorDetail = ErrorDetail(
            ErrorCode.A_EXCEPTION.code,
            message = "A 익셉션을 강제로 에러를 발생시킵니다.",
            HttpStatus.BAD_REQUEST,
            mapOf("sample-key1" to "sample-value1", "sample-key2" to "sample-value2")
        )

        throw AException(errorDetail)
    }
}