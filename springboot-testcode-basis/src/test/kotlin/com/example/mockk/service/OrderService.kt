package com.example.mockk.service

class OrderService(
    private val orderClient: OrderClient
) {

    fun orderWithPayAmount(): String {
        return orderClient.order()
    }
}