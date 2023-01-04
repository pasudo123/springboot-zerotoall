package coroutine.example06

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FlowExample03

fun main() = runBlocking {
    launch {
        for (k in 1..3) {
            println("i'm not blocked $k")
            delay(1000)
        }
    }

    simpleFlow().collect { value -> println("==> $value") }
}

fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        // Thread.sleep 을 붙이게 되면 메인스레드가 블럭킹 된다.
        // Thread.sleep(1000)
        delay(1000)
        emit(i)
    }
}