package com.example.springbootconcurrencybasis.client.ticket

import com.example.springbootconcurrencybasis.client.ticket.resource.TicketResource
import retrofit2.Call

interface TicketClient {
    fun createTicket(
        id: Long,
        resource: TicketResource.TicketRequest
    ): Call<TicketResource.Reply>
}