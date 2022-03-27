package coroutine.example06

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class FlowExample02

fun main() = runBlocking {
    suspendSimple().forEach { value -> println("==> $value")}
}

suspend fun suspendSimple(): List<Int> {
    val list = listOf(1, 2, 3)
    // delay 만큼 시간을 기다리고, 바로 출력한다.
    delay(1000)
    return list
}