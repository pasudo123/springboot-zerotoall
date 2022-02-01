package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[1] Unconfined      : I'm working in thread main @coroutine#2
[2] Unconfined      : I'm working in thread main @coroutine#2
main runBlocking: I'm working in thread main @coroutine#3
[3] Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor @coroutine#2
main runBlocking: After delay in thread main @coroutine#3
 */
class Dispatchers02 {
    fun main() = runBlocking<Unit> {

        // 처음 호출될 시에는 호출자 스레드를 그대로 이어받아 수행된다. (첫번째 suspend 지점까지만)
        // UI 를 업데이트 하지 않는 코루틴에 적합하다. (특정 스레드에 구애받지 않는다.)
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("[1] Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            println("[2] Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            println("[3] Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }

        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
            delay(1000)
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
        }
    }
}