package coroutine.alone

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.jvm.Throws
import kotlin.system.measureTimeMillis

internal class AsyncExTest {

    @Test
    @DisplayName("async awaitAll() 을 수행, 그리고 에러를 만남.")
    fun asyncAwaitAllTest() {

        val elapsed = measureTimeMillis {
            runBlocking {
                try {
                    (1..10).map { seq ->
                        async(Dispatchers.IO) {
                            process(seq)
                        }
                    }.awaitAll()
                } catch (exception: Exception) {
                    println("handle error : ${exception.message}")
                }
            }
        }

        println("elapsed : ${elapsed}ms")
    }

    @Throws(RuntimeException::class)
    private suspend fun process(seq: Int) {

        println("do something ... : $seq")

        if (seq == 5) {
            throw RuntimeException("error !")
        }

        delay(1000L)
    }

    private fun throwError(seq: Int) {
        if (seq == 5) {
            throw RuntimeException("error !")
        }
    }
}
