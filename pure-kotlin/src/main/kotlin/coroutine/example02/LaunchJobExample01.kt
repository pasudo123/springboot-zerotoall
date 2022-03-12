package coroutine.example02

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LaunchJobExample01

fun main(): Unit = runBlocking {

    print("job.join() 을 수행하겠습니까? (y/n) : ")
    val yn = readLine()!!
    doSomething(yn)
    println("End ...")
}

suspend fun doSomething(yn: String) = coroutineScope {

    val job = launch {
        delay(1500)
        println("launch block called")

        launch {
            delay(500)
            println("sub launch block called")
        }
    }

    println("Hello")
    if (yn == "y") {
        job.join()
    }
    println("World")
}