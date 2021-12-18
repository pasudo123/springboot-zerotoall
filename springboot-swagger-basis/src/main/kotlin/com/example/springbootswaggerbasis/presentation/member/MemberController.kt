package com.example.springbootswaggerbasis.presentation.member

import com.example.springbootswaggerbasis.presentation.member.model.MemberRequest
import com.example.springbootswaggerbasis.presentation.member.model.MemberResponse
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("members")
class MemberController {

    private val log = LoggerFactory.getLogger(javaClass)
    private val idGenerator = AtomicLong(1)

    @PostMapping
    fun create(@RequestBody request: MemberRequest.Create): ResponseEntity<MemberResponse> {
        return ResponseEntity.ok(
            MemberResponse(
                id = idGenerator.getAndIncrement(),
                name = request.name,
                age = request.age,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
        )
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<MemberResponse>> {
        val memberResponses: MutableList<MemberResponse> = mutableListOf()

        repeat(10) { seq ->
            MemberResponse(
                id = seq.toLong(),
                name = "홍길동-$seq",
                age = seq,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
        }

        return ResponseEntity.ok(memberResponses)
    }

    @GetMapping("{id}")
    fun findOne(@PathVariable("id") id: Long): ResponseEntity<MemberResponse> {
        return ResponseEntity.ok(
            MemberResponse(
                id = id,
                name = "홍길동-$id",
                age = 30,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
        )
    }

    @GetMapping("search")
    fun searchMembers(
        @RequestParam("name", required = false) name: String?,
        @RequestParam("age", required = false) age: Int?,
        @RequestParam("createdAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) createdAt: LocalDateTime?,
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.ok(
            MemberResponse(
                id = 1L,
                name = name ?: "알 수 없음",
                age = age ?: -1,
                createdAt = createdAt ?: LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
        )
    }

    @PatchMapping("{id}/name")
    fun changeName(
        @PathVariable("id") id: Long,
        @RequestBody request: MemberRequest.Patch
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.ok(
            MemberResponse(
                id = 1L,
                name = request.name ?: "알 수 없음",
                age = request.age ?: -1,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
        )
    }

    @PatchMapping("{id}/age")
    fun changeAge(
        @PathVariable("id") id: Long,
        @RequestBody request: MemberRequest.Patch
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.ok(
            MemberResponse(
                id = 1L,
                name = request.name ?: "알 수 없음",
                age = request.age ?: -1,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
            )
        )
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable("id") id: Long
    ) {
        log.info("멤버 id[$id] 가 삭제되었습니다.")
    }
}