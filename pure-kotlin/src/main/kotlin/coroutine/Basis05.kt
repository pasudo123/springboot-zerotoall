package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/***
[main @coroutine#1] : Hello
[main @coroutine#2] : World !!
[main @coroutine#1] : Done
 */
class Basis05 {
    fun main() = runBlocking {
        val job = launch {
            delay(1000L)
            printlnWithThreadName("World !!")
        }

        printlnWithThreadName("Hello")
        // job.join() 을 함으로써 child coroutine 이 완료될 때까지 대기한다.
        job.join()
        printlnWithThreadName("Done")
    }
}