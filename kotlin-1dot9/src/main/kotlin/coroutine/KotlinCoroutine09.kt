package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

class KotlinCoroutine09

fun main() = runBlocking(Dispatchers.IO) {
    val job = launch { processFileWithRepeat("file123") }
    job.invokeOnCompletion { throwable ->
        println("invokeOnCompletion, throwable=${throwable?.message}")
    }
    delay(1200)
    job.cancelAndJoin()
//    job.join()

    println("done")
}

suspend fun processFileWithRepeat(file: String) {
    try {
        repeat(5) {
            println("$it process file=$file ...")
            delay(500)
            println("$it process file=$file done")
        }
    } catch (exception: CancellationException) {
        println("cancellationException message=${exception.message}")
    } catch (exception: Exception) {
        println("exception message=${exception.message}")
    } finally {
        println("finally")
    }
}