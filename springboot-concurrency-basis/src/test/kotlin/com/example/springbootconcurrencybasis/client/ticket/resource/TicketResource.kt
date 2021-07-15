package com.example.springbootconcurrencybasis.client.ticket.resource

import com.example.springbootconcurrencybasis.client.concert.resource.ConcertResource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class TicketResource {

    class TicketRequest(
        val name: String,
        val initCount: Long,
        val price: Long,
        val seatGrade: String
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Reply (
        val id: Long,
        val name: String,
        val initCount: Long,
        val price: Long,
        val seatGrade: String,
        val concert: ConcertResource.Reply? = null
    )
}