package objectmapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import objectmapper.ObjectMapperSample.mapper
import java.util.UUID

object ObjectMapperSample {
    val mapper = ObjectMapper().registerKotlinModule()
}

inline fun <reified T: Any> T.toJson(): String = mapper.writeValueAsString(this)
inline fun <reified T: Any> String.toObject(): T = mapper.readValue(this, T::class.java)
inline fun <reified T: Any> String.toList(): List<T> = mapper.readValue(this, object: TypeReference<List<T>>() {})

fun main() {
    val americano = Coffee("Americano")
    println(americano.toJson())

    val americanoJson = """
        {"name":"Americano"}
    """.trimIndent()
    val coffee = americanoJson.toObject<Coffee>()
    println(coffee.name)

    val coffees = listOf(Coffee("Americano1"), Coffee("Americano2"), Coffee("Americano3"))

    var results = coffees.toJson().toObject<List<Coffee>>()
    println("==> size : ${results.size}, coffees : $coffees")

    results = coffees.toJson().toList<Coffee>()
    println("==> size : ${results.size}, coffees : $coffees")
}

data class Coffee(
    val name: String,
    val uuid: String = UUID.randomUUID().toString()
)