package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

class KotlinCoroutine09

fun main9() = runBlocking(Dispatchers.IO) {
    val job = launch { processFileWithRepeat("sample.txt") }
    job.invokeOnCompletion { throwable ->
        if (throwable != null) {
            println("## 코루틴 수행 실패. throwable=${throwable.message}")
        } else {
            println("## 코루틴 수행 성공.")
        }
    }
    delay(1000)
//    job.cancel()
//    job.cancelAndJoin()
    job.join()

    println("done")
}

suspend fun processFileWithRepeat(file: String) {
    try {
        repeat(15) {
            println("$it process file=$file ...")
            delay(100)
            Thread.sleep(100)
            println("$it process file=$file done")
        }
    } catch (exception: CancellationException) {
        println("## cancellationException message=${exception.message}")
    } catch (exception: Exception) {
        println("## exception message=${exception.message}")
    } finally {
        println("## finally")
    }
}