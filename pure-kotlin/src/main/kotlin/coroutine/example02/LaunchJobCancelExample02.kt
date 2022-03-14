package coroutine.example02

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LaunchJobCancelExample02

fun main(): Unit = runBlocking {
    doSomethingV2()
}

suspend fun doSomethingV2() = coroutineScope {
    val startTime = System.currentTimeMillis()

    val job = launch(Dispatchers.Default) {
        var next = startTime
        var i = 0

        // cancelAndJoin() 을 호출했음에도 불구하고 계속 작동함 : cpu 자원 소모. -> isActive 를 사용해주어야 함
        // while (i < 5) {
        while (isActive) {
            if (System.currentTimeMillis() >= next) {
                println("job : I'm sleeping ${i++} ...")
                next += 800L
            }
        }
    }

    delay(1300L)
    println("main : tired of waiting")
    job.cancelAndJoin()
    println("main : quit")
}