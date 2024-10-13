package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KotlinCoroutine12

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {

    // kotlin 1.6 에서 @OptIn(ExperimentalCoroutinesApi::class) 실험적 단계인 코루틴 API
    val customDispatchersIO = Dispatchers.IO.limitedParallelism(3)
    val jobs = (1..50).map { i ->
        launch(customDispatchersIO) {
            println("thread=${Thread.currentThread().name}, current=$i, time=${System.currentTimeMillis()}")
            Thread.sleep(100)
        }
    }
    jobs.joinAll()
    println("end...")
}