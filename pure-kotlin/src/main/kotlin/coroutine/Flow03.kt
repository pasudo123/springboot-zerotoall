package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#1] : Calling simple function...
[main @coroutine#1] : Calling collect...
[main @coroutine#1] : Flow started
[main @coroutine#1] : first flow : 1
[main @coroutine#1] : first flow : 2
[main @coroutine#1] : first flow : 3
[main @coroutine#1] : Calling collect again...
[main @coroutine#1] : Flow started
[main @coroutine#1] : second flow : 1
[main @coroutine#1] : second flow : 2
[main @coroutine#1] : second flow : 3
 simple() 에 suspend 키워드가 없어서, 빠르게 호출되고 반환된다.
 그리고 별도로 기다리지 않는다.

 cold stream 이다. sequence 와 유사하다.
 */
class Flow03 {

    private fun simple(): Flow<Int> = flow {
        printlnWithThreadName("Flow started")
        for (i in 1..3) {
            delay(100L)
            emit(i)
        }
    }

    fun main() = runBlocking {
//        launch {
//            for (k in 1..10) {
//                delay(50L)
//                printlnWithThreadName("current K : $k")
//            }
//        }
        printlnWithThreadName("Calling simple function...")
        // 바로 실행되지 않는다. : collect { } 이 호출될 때 실행된다.
        val flow = simple()
        printlnWithThreadName("Calling collect...")
        flow.collect { value -> printlnWithThreadName("first flow : $value")}
        printlnWithThreadName("Calling collect again...")
        flow.collect { value -> printlnWithThreadName("second flow : $value") }
        printlnWithThreadName("Done")
    }
}