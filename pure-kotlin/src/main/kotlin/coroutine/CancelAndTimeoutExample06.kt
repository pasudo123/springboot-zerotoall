package coroutine

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
[main @coroutine#2] : job: I'm sleeping 0 ...
[main @coroutine#2] : job: I'm sleeping 1 ...
[main @coroutine#2] : job: I'm sleeping 2 ...
[main @coroutine#1] : main : I'm tired of waiting !
[main @coroutine#2] : [finally] New coroutine block start
[main @coroutine#2] : [finally] New coroutine block end
[main @coroutine#1] : main : now i can quit
 */
class CancelAndTimeoutExample06 {
    fun main() = runBlocking {

        val job = launch {
            try {
                repeat(1000) { i ->
                    printlnWithThreadName("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                // 특이 케이스
                // NonCancellable job 은 항상 활성화 상태다.
                // 취소 없이 실행해야 하는 코드블럭 내에서 수행할 수 있다.
                withContext(NonCancellable) {
                    // finally block 내부에 새로운 코루틴 블럭을 만들어서 또 다른 처리를 수행할 수 있다.
                    printlnWithThreadName("[finally] New coroutine block start")
                    delay(3000L)
                    printlnWithThreadName("[finally] New coroutine block end")
                }
            }
        }

        delay(1300L)
        printlnWithThreadName("main : I'm tired of waiting !")
        job.cancelAndJoin()
        printlnWithThreadName("main : now i can quit")
    }
}