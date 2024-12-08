package mutex

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore

class SemaphoreExample

val semaphore = Semaphore(2)

private suspend fun incrWitSemaphore(name: String) {
    if (semaphore.tryAcquire().not()) {
        println("do not acquire semaphore, $name")
        return
    }

    semaphore.acquire()
    try {
        println("enter-$name")
        delay(3000)
        println("exit-$name")
    } finally {
        semaphore.release()
    }
}

fun main() = runBlocking {
    val jobs = (1..10).map {
        launch { incrWitSemaphore("job_$it") }
    }

    jobs.joinAll()
}