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

    delay(1300L)
    println("main : tired of waiting")
    // job.cancelAndJoin()
    println("job isActive. : ${job.isActive}, job isCompleted. : ${job.isCompleted}")
    job.cancel()
    println("job isActive.. : ${job.isActive}, job isCompleted.. : ${job.isCompleted}")
    job.join()
    println("job isActive... : ${job.isActive}, job isCompleted... : ${job.isCompleted}")
    println("main : quit")
}