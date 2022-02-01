package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/**
[DefaultDispatcher-worker-1] : [1] Job Active : true
[DefaultDispatcher-worker-1] : Job : I'm sleeping 0 ...
[DefaultDispatcher-worker-1] : Job : I'm sleeping 1 ...
[DefaultDispatcher-worker-1] : Job : I'm sleeping 2 ...
[main] : main : I'm tired of waiting !
[DefaultDispatcher-worker-1] : [2] Job Active : false
[main] : main : now i can quit
 */
class CancelAndTimeoutExample04 {
    fun main() = runBlocking {

        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            printlnWithThreadName("[1] Job Active : $isActive")
            while (this.isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    // suspend 함수로 yield() 를 넣고 cancellable 이 되도록 한다.
                    yield()
                    printlnWithThreadName("Job : I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
            printlnWithThreadName("[2] Job Active : $isActive")
        }

        delay(1300L)
        printlnWithThreadName("main : I'm tired of waiting !")
        // 상태값을 가지고 종료시킨다.
        job.cancelAndJoin()
        printlnWithThreadName("main : now i can quit")
    }
}