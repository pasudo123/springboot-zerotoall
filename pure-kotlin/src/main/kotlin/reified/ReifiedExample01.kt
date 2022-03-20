package reified

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class ReifiedExample

val mapper = ObjectMapper().registerKotlinModule()

fun <T> T.toJson(): String = mapper.writeValueAsString(this)
fun <T> String.toObjectVersion1(clazz: Class<T>): T = mapper.readValue(this, clazz)
inline fun <reified T: Any> String.toObjectVersion2(): T = mapper.readValue(this, T::class.java)

