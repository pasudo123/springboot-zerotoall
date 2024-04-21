package com.example.springbootgrpcbasis.client

import com.example.proto.sample.HelloRequest
import com.example.proto.sample.HelloResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MockClient {

    @POST("/simple-protobuf/hello")
    fun hello(@Body request: HelloRequest): Call<HelloResponse>
}
