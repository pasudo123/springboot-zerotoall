package coroutine.example03

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class GlobalScopeExample03

var resultEx03 = ""

fun main() = runBlocking {
    loadConfigurationAndData()
}

/**
 * suspend 가 안붙은 동작은 실행시키려면 아래와 같은 방식을 이용한다.
 * 번갈아서 동시 실행을 하려면, fetchConfigurationV3, fetchDataV3 각각에 suspend 를 붙여주어야 한다.
 */
suspend fun loadConfigurationAndData() {
    coroutineScope {
        launch { fetchConfigurationV3() }
        launch { fetchDataV3() }
        println("completed !!")
    }
}

fun fetchConfigurationV3(): String {
    var i = 0
    while (i < 10) {
        Thread.sleep(25)
        print("[${Thread.currentThread().name}] fetch configuration.")
        repeat(i) { print(".") }
        println()
        i++
    }
    return "configuration-${Random.nextLong(1000, 9999)}"
}

fun fetchDataV3(): String {
    var i = 0
    while (i < 10) {
        Thread.sleep(25)
        print("[${Thread.currentThread().name}] fetch data.")
        repeat(i) { print(".") }
        println()
        i++
    }

    return "data-${Random.nextLong(1000, 9999)}"
}
