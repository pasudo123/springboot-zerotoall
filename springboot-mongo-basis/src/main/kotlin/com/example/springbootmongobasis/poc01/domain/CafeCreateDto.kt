package com.example.springbootmongobasis.poc01.domain

data class CafeCreateDto(
    val name: String,
    val coffees: List<CoffeeDto>,
    val beverages: List<BeverageDto>,
    val tees: List<TeeDto>
) {

    data class CoffeeDto(
        val name: String,
        val price: Long,
        val remark: String
    )

    data class BeverageDto(
        val name: String,
        val price: Long,
        val remark: String
    )

    data class TeeDto(
        val name: String,
        val price: Long,
        val remark: String
    )
}