package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
[main @coroutine#1] : Useful[1]
[main @coroutine#1] : Useful[2]
[main @coroutine#1] : result :: 42
[main @coroutine#1] : Completed 2031 ms

 코루틴 코드임에도 불구하고 순차적으로 시행할 수 있다.
 */
class ComposingSuspendingFunction01 {
    fun main() {
        runBlocking {
            val time = measureTimeMillis {
                val one = doSomethingUsefulOne()
                val two = doSomethingUsefulTwo()
                printlnWithThreadName("result :: ${one + two}")
            }
            printlnWithThreadName("Completed $time ms")
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
        printlnWithThreadName("Useful[1]")
        delay(1000L)    //  가상으로 api 호출 또는 디비와 통신했음을 이야기한다.
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        printlnWithThreadName("Useful[2]")
        delay(1000L)    //  가상으로 api 호출 또는 디비와 통신했음을 이야기한다.
        return 29
    }
}