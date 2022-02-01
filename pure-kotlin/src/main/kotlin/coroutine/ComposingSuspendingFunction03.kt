package coroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class ComposingSuspendingFunction03 {
    fun main() {
        runBlocking {
            val time = measureTimeMillis {
                val oneDeferred = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
                val twoDeferred = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }

                // start 가 있고 없고에 따라 async coroutine 을 동시에 실행하는지 여부를 결정할 수 있다.
                oneDeferred.start()
                twoDeferred.start()

                printlnWithThreadName("result :: ${oneDeferred.await() + twoDeferred.await()}")
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