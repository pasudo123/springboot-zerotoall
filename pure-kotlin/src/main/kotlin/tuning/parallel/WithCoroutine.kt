package tuning.parallel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import tuning.parallel.Converter.filePath
import tuning.parallel.Converter.toPerson
import java.io.File
import java.util.concurrent.ForkJoinPool
import java.util.stream.Collectors
import kotlin.system.measureTimeMillis

class WithCoroutine

/**
 * [읽기 + 가공] lineSequence() + lines.parallelStream().map {}.collect(Collectors.toList())
 * 10회 평균 : 1169ms, 1041ms, 988ms
 *
 * [가공] lines.parallelStream().map {}.collect(Collectors.toList())
 * 10회 평균 : 688ms, 708ms
 *
 * [읽기 + 가공] lineSequence() + lines.map {}
 * 10회 평균 : 1169ms, 1101ms, 1109ms, 1064ms
 *
 * lineSequence() + lines.chunked().map { async {} }.awaitAll().flatten()
 * 10회 평균 : 1156ms, 1212ms, 1217ms, 1254ms
 *
 * https://www.baeldung.com/java-future
 */
fun main() = runBlocking(Dispatchers.Default) {

    val size = 20
    var sum = 0L
    val lines = mutableListOf<String>()

    File(filePath).bufferedReader().use { br ->
        br.lineSequence().forEach { line ->
            lines.add(line)
        }
    }

    repeat(size) {
        val elapsed = measureTimeMillis {

//            val persons = lines.useBasic()

            val persons = lines.useParallelStream()

//            val persons = lines
//                .chunked(1000)
//                .useCoroutineWithAsyncWait()

//                .map { async { convertWithSuspend(it) }
//                }
//                .awaitAll()
//                .flatten()

            // println(persons.size)
        }

        sum += elapsed
        println("lines size : ${lines.size}, elapsed[$it] : ${elapsed}ms")
    }

    println("평균 : ${sum / size}ms")
}

private fun List<String>.useBasic(): List<LongLongPerson> {
    return this.map { line -> toPerson(line) }
}

/**
 * https://www.baeldung.com/java-8-parallel-streams-custom-threadpool
 * https://junghyungil.tistory.com/103
 */
private fun List<String>.useParallelStream(): List<LongLongPerson> {
    val lines = this
    var result: List<LongLongPerson> = emptyList()
    var customThreadPool: ForkJoinPool? = null
    try {
        customThreadPool = ForkJoinPool(4)
        customThreadPool.submit {
            result = lines
//                .chunked(5000)
                .parallelStream()
                .map { chunk ->
                    toPerson(chunk)
                }
//                .flatMap(Collection<LongLongPerson>::stream)
                .collect(Collectors.toList())
        }.get()
    } finally {
        customThreadPool?.shutdown()
    }

    return result
}

private suspend fun List<List<String>>.useCoroutineWithAsyncWait(): List<LongLongPerson> = runBlocking(Dispatchers.IO) {
    map { async { convertChunkToPersonWithSuspend(it) } }
        .awaitAll()
        .flatten()
}

private suspend fun convertChunkToPersonWithSuspend(chunk: List<String>): List<LongLongPerson> = coroutineScope {
    chunk.map { line ->
        toPerson(line)
    }
}

private fun convertChunkToPerson(chunk: List<String>): List<LongLongPerson> {
    return chunk.map { line ->
        toPerson(line)
    }
}