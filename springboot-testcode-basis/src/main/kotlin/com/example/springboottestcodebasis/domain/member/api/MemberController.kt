package com.example.springboottestcodebasis.domain.member.api

import com.example.springboottestcodebasis.domain.member.model.Member
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.example.springboottestcodebasis.domain.member.service.MemberService
import mu.KLoggable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("members")
@Transactional
class MemberController(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository
) {

    companion object : Any(), KLoggable {
        override val logger = logger()
    }


    @PostMapping
    fun create(@RequestBody member: Member): ResponseEntity<Member> {
        logger.info { "[controller] :: create member controller" }
        val foundMember = memberRepository.save(member)
        return ResponseEntity.ok(foundMember)
    }

    @GetMapping
    fun fetchAll(): ResponseEntity<List<Member>> {
        val members = memberRepository.findAll()
        return ResponseEntity.ok(members)
    }

    @PatchMapping("{id}/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateName(
        @PathVariable("id") id: Long,
        @RequestBody member: Member
    ) {
        memberService.updateName(id, member.name!!)
    }

    @PatchMapping("{id}/age")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateAge(
        @PathVariable("id") id: Long,
        @RequestBody member: Member
    ) {
        memberService.updateAge(id, member.age!!)
    }

    @GetMapping("{id}")
    fun fetchOneById(@PathVariable("id") id: Long) : ResponseEntity<Member> {
        val memberOpt = memberRepository.findById(id)
        if (memberOpt.isPresent.not()) {
            throw EntityNotFoundException("Does not exist member[$id]")
        }

        return ResponseEntity.ok(memberOpt.get())
    }
}