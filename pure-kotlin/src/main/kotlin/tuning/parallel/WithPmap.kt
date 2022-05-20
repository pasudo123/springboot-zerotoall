package tuning.parallel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class WithPmap

fun main() = runBlocking(Dispatchers.IO) {
    val elapsed = measureTimeMillis {
        val output = (1..100).parallelMap {
            delay(1000L)
            it * 2
        }

        println(output)
    }

    println("total elapsed : $elapsed")
}

private suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> (B)): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}