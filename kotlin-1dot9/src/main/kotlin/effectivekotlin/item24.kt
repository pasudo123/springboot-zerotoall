package effectivekotlin

class item24

data class BoxOut<out T>(
    val value: T
)

data class BoxIn<in T : Any>(
    val value: Long
)

fun main() {

    val names = listOf("Hong", "Park")

    val nameLengths: List<Long> = runCatching {
        names.map { it.length }
        throw RuntimeException("강제에러")
    }.onSuccess {
        println("success")
    }.onFailure { exception ->
        println("fail. message=${exception.message}")
    }.getOrElse {
        println("getOrElse")
        listOf(1, 2, 3)
    }

    println(nameLengths)

    val boxOut1: BoxOut<Int> = BoxOut(5555)
    val boxOut2: BoxOut<Any> = boxOut1
    println(boxOut2.value)

    val boxIn1: BoxIn<Any> = BoxIn(4444)
    val boxIn2: BoxIn<Long> = boxIn1
    println(boxIn2.value)
}
