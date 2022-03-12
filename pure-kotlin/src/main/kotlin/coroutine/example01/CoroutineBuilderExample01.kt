package coroutine.example01

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineBuilderExample01

fun main() {

    print("Enter Number(1/2) : ")
    val input = readLine()
    val number: Int

    try {
       number = input!!.toInt()
    } catch (exception: Exception) {
        println(exception.message)
        return
    }

    println("Hello")
    when (number) {
        1 -> {
            runBlocking {
                delay(1000L)
                println("World [runBlocking]")
                println("World [runBlocking] one more time")
            }
        }
        2 -> {
            runBlocking {
                suspendFunc()
            }
        }
        else -> {
            runBlocking {
                launch {
                    delay(1000L)
                    println("World - 1000ms")
                }
                launch {
                    delay(500L)
                    println("World - 500ms")
                }
                println("World - 0ms")
            }
        }
    }

    println("End...")
}

suspend fun suspendFunc() = coroutineScope {
    launch {
        delay(1000L)
        println("World [coroutineScope]")
    }
    println("World [coroutineScope] one more time")
}