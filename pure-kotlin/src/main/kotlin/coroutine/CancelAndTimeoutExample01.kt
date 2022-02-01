package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#2] : Job : I'm sleeping 0
[main @coroutine#2] : Job : I'm sleeping 1
[main @coroutine#2] : Job : I'm sleeping 2
[main @coroutine#1] : main : I'm tired of waiting
[main @coroutine#1] : main : now i can quit
 */
class CancelAndTimeoutExample01 {
    fun main() = runBlocking {
        val job = launch {
            repeat(1000) { i ->
                printlnWithThreadName("Job : I'm sleeping $i")
                delay(500L)
            }
        }

        delay(1300L)
        printlnWithThreadName("main : I'm tired of waiting")
        job.cancel() // 현재 진행된 coroutine 을 취소시킨다.
        job.join()
        printlnWithThreadName("main : now i can quit")
    }
}