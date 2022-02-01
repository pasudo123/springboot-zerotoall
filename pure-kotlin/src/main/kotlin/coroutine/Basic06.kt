package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class Basic06

// 정상적으로 동작함
/*
fun main() = runBlocking {
    repeat(10_000) {
        launch {
            delay(10000L)
            print(".")
        }
    }
}
*/

// OutOfMemory 가 발생함
fun main() {
    repeat(10_000) {
        thread {
            Thread.sleep(1000L)
            print(".")
        }
    }
}