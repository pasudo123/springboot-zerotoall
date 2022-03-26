package com.example.springbootqueryperfbasis.domain.member

import com.example.springbootqueryperfbasis.domain.membeQMember.member
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberCustomRepository(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Member::class.java) {

    fun findAllByUniqIds(uniqIds: List<String>): List<Member> {
        return queryFactory.selectFrom(member)
            .where(member.memberUniqId.`in`(uniqIds))
            .fetch()
    }
}