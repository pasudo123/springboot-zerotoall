package com.example.springbootconcurrencybasis.client.booking

import com.example.springbootconcurrencybasis.client.booking.resource.BookingResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BookingRealClient : BookingClient {

    @POST("bookings")
    override fun createBooking(
        @Body resource: BookingResource.CreateRequest
    ): Call<BookingResource.Reply>
}