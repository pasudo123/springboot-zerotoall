package coroutine.example03

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class GlobalScopeExample02

var resultEx02 = ""

fun main() = runBlocking {
    loadConfigurationV2()
}

suspend fun loadConfigurationV2() {
    val config = fetchConfigurationServerV2()
    updateConfigurationV2(config)

    println("[${Thread.currentThread().name}] result : $resultEx02")
}

suspend fun fetchConfigurationServerV2(): String {
    var i = 0
    while (i < 100) {
        delay(50)
        print("[${Thread.currentThread().name}] fetch configuration server data.")
        repeat(i) { print(".") }
        println()
        i++
    }

    return "configuration-${Random.nextLong(1000, 9999)}"
}

fun updateConfigurationV2(config: String) {
    resultEx02 = config
}