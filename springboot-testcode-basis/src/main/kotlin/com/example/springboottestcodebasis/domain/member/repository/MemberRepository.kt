package com.example.springboottestcodebasis.domain.member.repository

import com.example.springboottestcodebasis.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>