package chapter01

class item05

fun main() {
    // IllegalArgumentException 이 발생
//     Person(age = -1, "홍길동")

    // IllegalStateException 이 발생
     val order = Order().apply { this.isOrdered = true }
     order.ordered()
}

data class Person(
    val age: Int,
    val name: String
) {
    init {
        require(age >= 0) {
            "나이는 음수가 될 수 없습니다. age=$age"
        }
    }
}

class Order {
    var isOrdered: Boolean = true

    fun ordered() {
        check(isOrdered.not()) {
            "주문된 상태입니다. ordered=$isOrdered"
        }
    }
}