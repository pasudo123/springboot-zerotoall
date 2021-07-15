package com.example.springbootconcurrencybasis.client.ticket

import com.example.springbootconcurrencybasis.client.ticket.resource.TicketResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface TicketRealClient : TicketClient {
    @POST("tickets/concerts/{id}")
    override fun createTicket(
        @Path("id") id: Long,
        @Body resource: TicketResource.TicketRequest
    ): Call<TicketResource.Reply>
}