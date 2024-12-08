package mutex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.internal.AtomicOp
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class Mutex01

var counter = 0
val counterAtomic = AtomicInteger(0)
val mutex = Mutex()

private fun reset() {
    counterAtomic.set(0)
    counter = 0
}

/**
 * mutex 처리. (suspend 와 같이 쓰임)
 * race condition 미발생.
 */
private suspend fun incrWithLock() {
    val milli = Random.nextLong(2)
    delay(milli)
    println("current counrter=$counter")
    mutex.withLock { counter++ }
}

/**
 * race condition 발생.
 */
private fun incr() {
    val milli = Random.nextLong(2)
    Thread.sleep(milli)
    counter++
}

/**
 * AtomicInteger CAS 연산으로 인한 처리
 * race condition 미발생.
 */
private fun incrWithAtomic() {
    val milli = Random.nextLong(2)
    Thread.sleep(milli)
    counterAtomic.incrementAndGet()
}

fun main() = runBlocking {
    repeat(20) {
        reset()

        val jobs = (1..1000).map {
            launch(Dispatchers.IO) { incrWithLock() }
        }

        jobs.joinAll()
        println("[$it] counter=$counter, counterAtomic=${counterAtomic.get()}")
    }
}