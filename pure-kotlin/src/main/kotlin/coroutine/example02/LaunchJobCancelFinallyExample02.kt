package coroutine.example02

import exercise01.printTypeWithReified
import exercise01.toJson
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class LaunchJobCancelFinallyExample02

fun main() = runBlocking {
    doSomethingV4()
}

suspend fun doSomethingV4() = coroutineScope {
    val job = launch {
        try {
            repeat(100) { seq ->
                println("job : I'm sleeping $seq...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job : I'm running finally")
                delay(500L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }

    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}