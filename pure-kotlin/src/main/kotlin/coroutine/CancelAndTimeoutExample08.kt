package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/**
[main @coroutine#1] : I'm sleeping 0 ...
[main @coroutine#1] : I'm sleeping 1 ...
[main @coroutine#1] : I'm sleeping 2 ...
[main @coroutine#1] : result : null

[main @coroutine#1] : I'm sleeping 0 ...
[main @coroutine#1] : I'm sleeping 1 ...
[main @coroutine#1] : I'm sleeping 2 ...
[main @coroutine#1] : I'm sleeping 3 ...
[main @coroutine#1] : I'm sleeping 4 ...
[main @coroutine#1] : result : Done
 */
class CancelAndTimeoutExample08 {
    fun main() = runBlocking {
        // timeout 이 발생하면 exception 발생대신에 null 을 리턴한다.
        val resultOfNull = withTimeoutOrNull(1300L) {
            repeat(5) { i->
                printlnWithThreadName("I'm sleeping $i ...")
                delay(500L)
            }
            "Done"
        }

        printlnWithThreadName("result : $resultOfNull")

        val resultOfDone = withTimeoutOrNull(5000L) {
            repeat(5) { i->
                printlnWithThreadName("I'm sleeping $i ...")
                delay(500L)
            }
            "Done"
        }

        printlnWithThreadName("result : $resultOfDone")
    }
}