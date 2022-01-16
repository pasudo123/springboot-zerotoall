package com.example.springbootqueryperfbasis.domain.member

import kotlin.random.Random

object MemberFixture {

    fun aEntity(): Member {
        val uniqueId = Random.nextLong(100, 999999999)
        return Member("홍길동$uniqueId")
    }
}