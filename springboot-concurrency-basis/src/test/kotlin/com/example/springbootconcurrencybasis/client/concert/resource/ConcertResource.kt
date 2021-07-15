package com.example.springbootconcurrencybasis.client.concert.resource

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class ConcertResource {

    class CreateRequest(
        val name: String,
        val performanceDate: String,
        val viewingTime: Long,
        val grade: String
    )

    /**
     * 필드 없는 것에 대해서 역직렬화 할 시, 에러 발생시키지 않는다.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Reply (
        val id: Long,
        val name: String,
        val performanceDate: String,
        val viewingTime: Long,
        val grade: String,
        val createdAt: String,
        val modifiedAt: String
    )
}