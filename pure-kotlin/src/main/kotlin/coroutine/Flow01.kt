package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Flow01 {
    fun main() {
        /** [1] 리스트를 반환하고 해당 리스트 값을 차례대로 출력한다. **/
//    simple().forEach {element -> println(element)}

        /** [2] cpu 자원을 사용하고 해당 작업은 ?? 의 시간이 소요되며 블락된다고 가정한다. **/
//    computeWithCpu().forEach { element -> println(element) }
//    printlnWithThreadName("out task")

        /** [3] suspend 를 통해서 main thread 는 blocking 없이 실행될 수 있다. **/
        runBlocking {
            launch {
                suspendComputeWithCpu().forEach { element -> println(element) }
            }
            printlnWithThreadName("another task ...")
        }
    }

    fun simple(): List<Int> = listOf(1, 2, 3)

    fun computeWithCpu(): Sequence<Int> = sequence { // sequence builder
        for (i in 1..3) {
            printlnWithThreadName("waiting $i...")
            Thread.sleep(1500L)
            yield(i)
        }
    }

    suspend fun suspendComputeWithCpu(): List<Int> {
        printlnWithThreadName("delay...")
        delay(2000L)
        return listOf(1, 2, 3)
    }
}