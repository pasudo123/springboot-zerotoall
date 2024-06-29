package com.example.springbootmongobasis.poc01.domain

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.poc01.api.CafeDto
import com.example.springbootmongobasis.poc01.domain.sub.Beverage
import com.example.springbootmongobasis.poc01.domain.sub.Coffee
import com.example.springbootmongobasis.poc01.domain.sub.Tee
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.persistence.Id

@Document(collection = "cafe")
class Cafe private constructor(
    @Field(name = "name")
    var name: String? = null,

    @Field(name = "coffees")
    val coffees: List<Coffee> = mutableListOf(),

    @Field(name = "beverages")
    val beverages: List<Beverage> = mutableListOf(),

    @Field(name = "tees")
    val tees: List<Tee> = mutableListOf(),
) : BaseDocument() {

    @Id
    @Field("_id")
    var id: ObjectId? = ObjectId()
        protected set

    companion object {
        fun from(cafeDto: CafeDto): Cafe {
            return Cafe(
                name = cafeDto.name,
                coffees = cafeDto.coffees.map { Coffee.from(it) },
                beverages = cafeDto.beverages.map { Beverage.from(it) },
                tees = cafeDto.tees.map { Tee.from(it) },
            )
        }
    }
}