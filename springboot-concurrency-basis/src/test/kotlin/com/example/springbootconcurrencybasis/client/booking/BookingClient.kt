package com.example.springbootconcurrencybasis.client.booking

import com.example.springbootconcurrencybasis.client.booking.resource.BookingResource
import retrofit2.Call

interface BookingClient {
    fun createBooking(
        resource: BookingResource.CreateRequest
    ): Call<BookingResource.Reply>
}