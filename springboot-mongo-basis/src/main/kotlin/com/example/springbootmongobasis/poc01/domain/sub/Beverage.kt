package com.example.springbootmongobasis.poc01.domain.sub

import com.example.springbootmongobasis.poc01.domain.CafeCreateDto
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Field

data class Beverage(
    @Field("name")
    var name: String,
    @Field("price")
    var price: Long,
    @Field("remark")
    var remark: String
) {
    @Field("id")
    var id: ObjectId? = ObjectId()
        private set

    companion object {
        fun from(beverageDto: CafeCreateDto.BeverageDto): Beverage {
            return Beverage(
                name = beverageDto.name,
                price = beverageDto.price,
                remark = beverageDto.remark
            )
        }
    }
}