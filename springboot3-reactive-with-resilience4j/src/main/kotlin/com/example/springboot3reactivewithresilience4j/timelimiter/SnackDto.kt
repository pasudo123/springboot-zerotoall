package com.example.springboot3reactivewithresilience4j.timelimiter

data class SnackDto(
    val message: String
) {

    companion object {
        fun of(message: String): SnackDto {
            return SnackDto(message)
        }
    }
}
