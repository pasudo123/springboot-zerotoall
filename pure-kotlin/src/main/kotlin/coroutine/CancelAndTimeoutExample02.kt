package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[DefaultDispatcher-worker-1] : Job : I'm sleeping 0 ...
[DefaultDispatcher-worker-1] : Job : I'm sleeping 1 ...
[DefaultDispatcher-worker-1] : Job : I'm sleeping 2 ...
[main] : main : I'm tired of waiting !
[DefaultDispatcher-worker-1] : Job : I'm sleeping 3 ...
[DefaultDispatcher-worker-1] : Job : I'm sleeping 4 ...
[main] : main : now i can quit
 */
class CancelAndTimeoutExample02 {
    fun main() = runBlocking {

        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0

            while(i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    printlnWithThreadName("Job : I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }

        delay(1300L)
        printlnWithThreadName("main : I'm tired of waiting !")
        // 코루틴 자체가 취소에 협조적이지 않았기 때문에 취소가 되지 않는다.
        // suspend 연산이 없기 때문이다.
        job.cancelAndJoin()
        printlnWithThreadName("main : now i can quit")
    }
}