package coroutine.example02

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LaunchJobCancelExample01

fun main(): Unit = runBlocking {
    doSomething()
}

suspend fun doSomething() = coroutineScope {
    launch {
        delay(1500L)
        println("Hello")
    }
}