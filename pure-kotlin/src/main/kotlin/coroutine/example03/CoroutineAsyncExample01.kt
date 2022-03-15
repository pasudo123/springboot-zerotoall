package coroutine.example03

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class CoroutineAsyncExample01

fun main() = runBlocking {

    val time = measureTimeMillis {
        val one = async { doSomethingAsyncV1() }
        val two = async { doSomethingAsyncV2() }
        println("The answer is ${one.await() + two.await()}")
    }

    println("completed in $time ms")
}

suspend fun doSomethingAsyncV1(): Long {
    delay(1000L)
    return 5L
}

suspend fun doSomethingAsyncV2(): Long {
    delay(1000L)
    return 10L
}