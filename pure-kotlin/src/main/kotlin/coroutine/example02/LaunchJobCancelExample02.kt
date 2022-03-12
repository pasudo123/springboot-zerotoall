package coroutine.example02

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LaunchJobCancelExample02

fun main(): Unit = runBlocking {
    doSomethingV2()
}

suspend fun doSomethingV2() = coroutineScope {
    val job = launch {

    }
}