package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#3] : Second child throws an exception
[main @coroutine#2] : First child coroutine was cancelled
Computation failed with ArithmeticException

 서브 코루틴에 익셉션 발생 시, 상위 코루틴도 같이 취소가 된다.
 */
class ComposingSuspendingFunction04 {
    fun main() {
        runBlocking<Unit> {
            try {
                println("==> start")
                failedConcurrentSum()
                println("==> end")
            } catch(e: ArithmeticException) {
                println("Computation failed with ArithmeticException")
            }
        }
    }

    suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(5000L)
                500
            } finally {
                printlnWithThreadName("First child coroutine was cancelled")
            }
        }

        val two = async<Int> {
            printlnWithThreadName("Second child throws an exception")
            throw ArithmeticException()
        }

        one.await() + two.await()
    }
}