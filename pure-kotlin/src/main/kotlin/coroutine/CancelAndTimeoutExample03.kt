package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/**
[DefaultDispatcher-worker-1 @coroutine#2] : Job : I'm sleeping 0 ...
[DefaultDispatcher-worker-1 @coroutine#2] : Job : I'm sleeping 1 ...
[DefaultDispatcher-worker-1 @coroutine#2] : Job : I'm sleeping 2 ...
[main @coroutine#1] : main : I'm tired of waiting !
[DefaultDispatcher-worker-1 @coroutine#2] : Exception : kotlinx.coroutines.JobCancellationException: StandaloneCoroutine was cancelled; job="coroutine#2":StandaloneCoroutine{Cancelling}@4c894f14
[main @coroutine#1] : main : now i can quit
 */
class CancelAndTimeoutExample03 {
    fun main() = runBlocking {

        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            try {
                var nextPrintTime = startTime
                var i = 0

                while (i < 5) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        // suspend 함수로 yield() 를 넣고 cancellable 이 되도록 한다.
                        yield()
                        printlnWithThreadName("Job : I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            } catch (exception: Exception) {
                printlnWithThreadName("Exception : $exception")
            }
        }

        delay(1300L)
        printlnWithThreadName("main : I'm tired of waiting !")
        // 익셉션을 던져서 코루틴을 종료시킨다.
        job.cancelAndJoin()
        printlnWithThreadName("main : now i can quit")
    }
}