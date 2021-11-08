package com.example.springboottestcodebasis.domain.member.api

import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("members")
@Transactional
class MemberController(
    private val memberRepository: MemberRepository
) {

    @PostMapping
    fun create(@RequestBody member: Member): ResponseEntity<Member> {
        val foundMember = memberRepository.save(member)
        return ResponseEntity.ok(foundMember)
    }

    @GetMapping
    fun fetchAll(): ResponseEntity<List<Member>> {
        val members = memberRepository.findAll()
        return ResponseEntity.ok(members)
    }

    @GetMapping("{id}")
    fun fetchOneById(@PathVariable("id") id: Long) : ResponseEntity<Member> {
        val memberOpt = memberRepository.findById(id)
        if (memberOpt.isEmpty) {
            throw EntityNotFoundException("Does not exist member[$id]")
        }

        return ResponseEntity.ok(memberOpt.get())
    }
}