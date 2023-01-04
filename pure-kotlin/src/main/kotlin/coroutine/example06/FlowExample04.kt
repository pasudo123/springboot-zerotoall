package coroutine.example06

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class FlowExample04

/**
 * flow.collect {} 를 수행하기 전까지 flow 블럭을 실행하지 않는다. -> 콜드스트림
 */
fun main() = runBlocking {
    println("Calling Simple Function...")
    val flow = coldStreamFlow()

    println("Calling Collect...")
    flow.collect { value -> println("[1] ==> $value") }

    println("Calling Collect Again...")
    flow.collect { value -> println("[2] ==> $value") }
}

fun coldStreamFlow(): Flow<Int> = flow {
    println(">> Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}