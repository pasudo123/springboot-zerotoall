package coroutine.example04

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ParentCoroutineExample01

fun main() = runBlocking {

    val request = launch {
        repeat(3) { i ->
            launch {
                delay((i + 1) * 200L)
                println("child coroutine[$i] context")
            }
        }

        println("parent coroutine context")
    }

    request.join()
    println("done")
}