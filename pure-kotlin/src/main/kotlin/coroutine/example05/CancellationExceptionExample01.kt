package coroutine.example05

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CancellationExceptionExample01

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler $exception")
    }

    val job = GlobalScope.launch(handler) {
        // 첫번째 자식
        launch {
            try {
                println("first child")
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("[1] withContext")
                    delay(100)
                    println("[2] withContext")
                }
            }
        }

        // 두번째 자식
        launch {
            delay(10)
            println("second child")
            throw ArithmeticException()
        }
    }
    job.join()
}