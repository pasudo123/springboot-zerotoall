package com.example.springbootconcurrencybasis.client.booking

import com.example.springbootconcurrencybasis.client.booking.resource.BookingResource
import retrofit2.Call

class BookingLocalClient : BookingClient{
    override fun createBooking(
        resource: BookingResource.CreateRequest
    ): Call<BookingResource.Reply> {
        TODO("Not yet implemented")
    }
}