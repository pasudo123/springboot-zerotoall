package com.example.springbootconcurrencybasis.client.booking.resource

import com.example.springbootconcurrencybasis.client.concert.resource.ConcertResource
import com.example.springbootconcurrencybasis.client.ticket.resource.TicketResource
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class BookingResource {

    class CreateRequest(
        val concertId: Long,
        val ticketId: Long
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Reply (
        val id: Long,
        val concert: ConcertResource.Reply? = null,
        val ticket : TicketResource.Reply? = null
    )
}