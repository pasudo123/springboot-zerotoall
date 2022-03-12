package coroutine.example02

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LaunchJobCancelExample01

fun main(): Unit = runBlocking {
    doSomethingV1()
}

suspend fun doSomethingV1() = coroutineScope {
    val job = launch {
        repeat(20) { seq ->
            println("job : current seq[${seq}] / active[${this.coroutineContext.job.isActive}]")
            delay(500L)
        }
    }

    delay(1600L)
    println("main : tired of waiting")
    // job.cancelAndJoin()
    job.cancel()
    job.join()
    println("main : quit")
}