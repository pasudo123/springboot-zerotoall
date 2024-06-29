package com.example.springbootmongobasis.poc01.domain

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.poc01.domain.sub.Beverage
import com.example.springbootmongobasis.poc01.domain.sub.Coffee
import com.example.springbootmongobasis.poc01.domain.sub.Tee
import com.example.springbootmongobasis.util.toObjectIdOrNull
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
    var id: String? = null
        protected set

    companion object {
        fun from(cafeCreateDto: CafeCreateDto): Cafe {
            return Cafe(
                name = cafeCreateDto.name,
                coffees = cafeCreateDto.coffees.map { Coffee.from(it) },
                beverages = cafeCreateDto.beverages.map { Beverage.from(it) },
                tees = cafeCreateDto.tees.map { Tee.from(it) },
            )
        }
    }

    fun getMenuById(id: String) {
        val coffee = id.toObjectIdOrNull()?.let { menuId -> coffees.find { menuId == it.id } }
        val beverage = id.toObjectIdOrNull()?.let { menuId -> beverages.find { menuId == it.id } }
        val tee = id.toObjectIdOrNull()?.let { menuId -> tees.find { menuId == it.id } }
    }
}