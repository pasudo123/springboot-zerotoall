package coroutine.example06

import kotlinx.coroutines.runBlocking

class FlowExample01

fun main() = runBlocking {
    simpleSequence().forEach { value -> println("==> $value") }
    simpleCollection().forEach { value -> println("==> $value") }
}

// sequence builder 를 만들어준다.
fun simpleSequence(): Sequence<Int> = sequence {
    for (i in 1..3) {
        // 처리시간이 1000ms 걸리는 작업이다.
        println("thread 진입 전 : [1] $i")
        Thread.sleep(1000)
        yield(i)
        println("thread 진입 후 : [2] $i")
    }
}

fun simpleCollection(): List<Int> {
    val list = listOf(1, 2, 3)
    for (i in list.indices) {
        Thread.sleep(1000)
    }
    return list
}