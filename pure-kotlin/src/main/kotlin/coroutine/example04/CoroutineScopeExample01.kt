package coroutine.example04

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineScopeExample01

private val ioScope = CoroutineScope(Dispatchers.IO)
private val defaultScope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {

    processV1()

    // 3초 기다렸단가 coroutineScope 를 중단시킨다.
    delay(3000)
    ioScope.cancel()
    defaultScope.cancel()
    println("== exit")
}

suspend fun processV1() {
    val ioJob = ioScope.launch {
        repeat(20) {
            delay(300L)
            println("ioScope coroutine")
        }
    }

    val defaultJob = defaultScope.launch {
        repeat(20) {
            delay(300L)
            println("defaultScope coroutine")
        }
    }

    println("== done")
}