package com.example.springboottestcodebasis.domain.member.service

import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun updateName(id: Long, name: String) {
        val foundMember = memberRepository.findById(id)
            .orElseThrow { EntityNotFoundException("does not found member[$id]") }
        foundMember.updateName(name)
    }

    fun updateAge(id: Long, age: Int) {
        val foundMember = memberRepository.findById(id)
            .orElseThrow { EntityNotFoundException("does not found member[$id]") }
        foundMember.updateAge(age)
    }
}