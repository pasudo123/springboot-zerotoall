package com.example.springbooterrorbasis.message.api

import com.example.springbooterrorbasis.error.CustomException
import com.example.springbooterrorbasis.error.ErrorDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("messages")
class MessageController {

    @GetMapping
    fun getMessage(): ResponseEntity<String> {
        return ResponseEntity.ok(UUID.randomUUID().toString())
    }

    @GetMapping("{id}")
    fun getMessageWithPath(
        @PathVariable("id") id: Long
    ): ResponseEntity<String> {
        return ResponseEntity.ok("[$id] : ${UUID.randomUUID()}")
    }

    @GetMapping("param")
    fun getMessageWithRequestParam(
        @RequestParam("contents") contents: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(contents)
    }

    @PostMapping
    fun createMessage(
        @RequestBody(required = false) messageDto: MessageDto.CreateRequest? = null
    ): ResponseEntity<String> {
        return ResponseEntity.ok(messageDto?.message ?: "body 가 없다.")
    }
}