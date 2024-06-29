package com.example.springbootmongobasis.init.sub

import com.example.springbootmongobasis.poc01.domain.Cafe
import com.example.springbootmongobasis.poc01.domain.CafeCreateDto
import com.example.springbootmongobasis.poc01.domain.CafeRepository
import com.example.springbootmongobasis.util.toLineString
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class DataCafeInitializer(
    private val cafeRepository: CafeRepository
) {

    fun bulkInsert() {
        repeat(5) { cafeRepository.insert(Cafe.from(randomCafeDto())) }
    }

    private fun randomCafeDto(): CafeCreateDto {
        val uuidLine = UUID.randomUUID().toLineString()
        return CafeCreateDto(
            name = "인투더썬-$uuidLine",
            coffees = (1..(Random.nextLong(25))).map {
                CafeCreateDto.CoffeeDto(
                    name = "아메리카노-$uuidLine",
                    price = Random.nextLong(10L),
                    remark = "empty"
                )
            },
            beverages = (1..(Random.nextLong(25))).map {
                CafeCreateDto.BeverageDto(
                    name = "초코음료-$uuidLine",
                    price = Random.nextLong(10L),
                    remark = "empty"
                )
            },
            tees = (1..(Random.nextLong(25))).map {
                CafeCreateDto.TeeDto(
                    name = "얼그레이-$uuidLine",
                    price = Random.nextLong(10L),
                    remark = "empty"
                )
            },
        )
    }
}