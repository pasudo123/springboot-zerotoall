package coroutine.example04

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

class JobInContextExample01

fun main() = runBlocking {
    println("[${Thread.currentThread().name}] ${coroutineContext[Job]}")
    println("[${Thread.currentThread().name}] ${coroutineContext[Job]?.isActive}")
    println("[${Thread.currentThread().name}] ${coroutineContext[Job]?.isCancelled}")
    println("[${Thread.currentThread().name}] ${coroutineContext[Job]?.isCompleted}")
}

