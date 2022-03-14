package coroutine.example02

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LaunchJobCancelFinallyExample01

fun main() = runBlocking {
    doSomethingV3()
}

suspend fun doSomethingV3() = coroutineScope {
    val job = launch {
        try {
            repeat(100) { seq ->
                println("job : I'm sleeping $seq...")
                delay(500L)
            }
        } finally {
            println("job : I'm running finally")
        }
    }

    delay(1300L)
    println("main : tired of waiting")
    job.cancelAndJoin()
    println("main : quit")
}