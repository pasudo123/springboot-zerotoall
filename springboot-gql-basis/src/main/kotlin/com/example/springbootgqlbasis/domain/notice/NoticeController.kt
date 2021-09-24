package com.example.springbootgqlbasis.domain.notice

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@Transactional
@RestController
@RequestMapping("notices")
class NoticeController(
    private val noticeRepository: NoticeRepository
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<Notice>> {
        val notices = noticeRepository.findAll()
        return ResponseEntity.ok(notices)
    }

    @GetMapping("{id}")
    fun findOneById(@PathVariable id: Long): ResponseEntity<Notice> {
        val notice = noticeRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("공지사항을 찾지 못했습니다.")
        return ResponseEntity.ok(notice)
    }
}