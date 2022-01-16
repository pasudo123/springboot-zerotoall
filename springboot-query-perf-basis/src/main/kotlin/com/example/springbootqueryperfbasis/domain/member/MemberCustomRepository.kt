package com.example.springbootqueryperfbasis.domain.member

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberCustomRepository(
    private val queryFactory: JPAQueryFactory
) {
}