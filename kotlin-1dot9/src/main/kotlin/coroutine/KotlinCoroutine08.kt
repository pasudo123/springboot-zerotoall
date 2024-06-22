package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KotlinCoroutine08

/**
 * files.map { file ->
 *         async { processFile(file) }
 *     }.awaitAll()
 */
fun main() = runBlocking(Dispatchers.IO) {
    val files = listOf("file1", "file2", "file3", "file4", "file5")

    val jobs = files.map { file ->
        launch { processFile(file) }
    }

    jobs.forEach { job ->
        // StandaloneCoroutine{Active}@6e51066e
        println(job)
    }

    jobs.joinAll()

    jobs.forEach { job ->
        // StandaloneCoroutine{Completed}@24711d71
        println(job)
    }

    println("done")
}

private suspend fun processFile(file: String) {
    println("process file=$file ...")
    delay(2000)

    println("process file=$file done")
}