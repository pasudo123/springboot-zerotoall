package coroutine.example01

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineBuilderExample02

fun main() {
    runBlocking {
        doSomething()
        println("End...")
    }
}

suspend fun doSomething() = coroutineScope {
    launch {
        delay(2000L)
        println("World 2")
    }

    launch {
        delay(1000L)
        println("World 1")
    }

    println("Hello")
}