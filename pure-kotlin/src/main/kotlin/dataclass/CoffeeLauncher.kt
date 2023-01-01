package dataclass

data class Coffee(
    val name: String,
    val price: Long
) {
    var properties: MutableMap<String, Any> = mutableMapOf()
}

fun main() {

    val americano = Coffee("아메리카노", 4500L).apply {
        this.properties["prop1"] = "test"
    }

    println(americano.toString())

    val newAmericano = americano.copy()

    println(americano == newAmericano)

    val (name, price) = newAmericano
    println("name : $name, price : $price")
}