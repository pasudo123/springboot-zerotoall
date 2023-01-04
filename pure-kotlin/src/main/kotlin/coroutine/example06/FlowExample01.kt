package coroutine.example06

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class FlowExample01

fun main() = runBlocking {
    simpleWithSequence().forEach { value -> println("==> $value : ${LocalDateTime.now()}") }
    println("\n************************************************\n")
    simpleCollection().forEach { value -> println("==> $value : ${LocalDateTime.now()}") }
    println("\n************************************************\n")
    simpleWithSuspend().forEach { value -> println("==> $value : ${LocalDateTime.now()}") }
    println("\n************************************************\n")
    simpleWithFlow().collect { value -> println("==> $value : ${LocalDateTime.now()}") }
}

// sequence builder 를 만들어준다.
fun simpleWithSequence(): Sequence<Int> = sequence {
    for (i in 1..3) {
        // 처리시간이 1000ms 걸리는 작업이다.
        someTask()
        yield(i)
    }
}

fun simpleCollection(): List<Int> {
    val list = listOf(1, 2, 3)
    for (i in list.indices) {
        someTask()
    }
    return list
}

suspend fun simpleWithSuspend(): List<Int> {
    someTaskWithDelay()
    return listOf(1, 2, 3)
}

fun simpleWithFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        someTaskWithDelay()
        emit(i)
    }
}

private fun someTask() {
    println("do task() : ${LocalDateTime.now()}")
    Thread.sleep(1000)
}

private suspend fun someTaskWithDelay() {
    println("do task() : ${LocalDateTime.now()}")
    delay(1000)
}