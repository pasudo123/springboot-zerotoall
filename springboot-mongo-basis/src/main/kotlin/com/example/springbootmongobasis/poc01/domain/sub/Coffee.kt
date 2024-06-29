package com.example.springbootmongobasis.poc01.domain.sub

import com.example.springbootmongobasis.poc01.api.CafeDto
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Field

data class Coffee(
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
        fun from(coffeeDto: CafeDto.CoffeeDto): Coffee {
            return Coffee(
                name = coffeeDto.name,
                price = coffeeDto.price,
                remark = coffeeDto.remark
            )
        }
    }
}