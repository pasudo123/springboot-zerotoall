package chapter01

import kotlin.properties.Delegates

class item08

fun main() {

}

data class PersonV8(
    val age: Int,
    val name: String
) {
    var number by Delegates.notNull<Long>()
    lateinit var money: Money
}

data class Money(
    val value: Long
)