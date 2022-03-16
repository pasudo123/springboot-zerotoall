package coroutine.example03

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class GlobalScopeExample01

var resultEx01 = ""

fun main() = runBlocking {
    loadConfiguration()
}

fun loadConfiguration() {
    GlobalScope.launch {
        val config = fetchConfigurationServer()
        updateConfiguration(config)
        println("[${Thread.currentThread().name}] GlobalScope block : $resultEx01")
    }

    Thread.sleep(1500)
    println("[${Thread.currentThread().name}] result : $resultEx01")
}

/**
 * network delay 가 있다고 가정한다.
 * GlobalScope 는 별도 백그라운드에서 돌고 있을 것이다. 마치 데몬처럼!
 * 오래걸리는 만큼 리소스는 더 낭비될 것이고, 완료시키지 못하고 메인 스레드는 종료된다.
 * 결국 loadConfiguration() 을 지속적으로 호출하게 된다면, 더 많은 낭비가 발생하게 될 것이다.
 */
fun fetchConfigurationServer(): String {
    var i = 0
    while (i < 100) {
        Thread.sleep(50)
        print("[${Thread.currentThread().name}] fetch configuration server data.")
        repeat(i) { print(".") }
        println()
        i++
    }

    return "configuration-${Random.nextLong(1000, 9999)}"
}

fun updateConfiguration(config: String) {
    resultEx01 = config
}