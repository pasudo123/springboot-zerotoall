package coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#1] : Hello
[main @coroutine#2] : First World
[main @coroutine#3] : Second World
[main @coroutine#1] : Done
 */
class Basis04 {
    fun main() = runBlocking {
        doWorld04()
        printlnWithThreadName("Done")
    }

    // 각각 개별 coroutineScope 내에서 실행된다.
    suspend fun doWorld04() = coroutineScope {
        launch {
            delay(1000L)
            printlnWithThreadName("First World ")
        }

        launch {
            delay(2000L)
            printlnWithThreadName("Second World")
        }

        printlnWithThreadName("Hello")
    }
}