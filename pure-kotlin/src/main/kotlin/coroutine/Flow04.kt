package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class Flow04 {

    private fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            printlnWithThreadName("Emitting $i")
            emit(i)
        }
    }

    fun main() = runBlocking {
        withTimeoutOrNull(300) {
            simple().collect  { value -> printlnWithThreadName("current value :: $value") }
        }
        printlnWithThreadName("Done")
    }
}