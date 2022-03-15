package coroutine.example03

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class CoroutineAsyncLazyExample01

fun main() = runBlocking {

    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingAsyncV1() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingAsyncV2() }

//        one.start()
        two.start()

        println("The answer is ${one.await() + two.await()}")
    }

    println("completed in $time ms")
}