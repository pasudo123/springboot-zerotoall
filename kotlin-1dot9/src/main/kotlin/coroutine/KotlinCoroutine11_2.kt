package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

class KotlinCoroutine11_1

/**
 * 에러가 부모 코루틴까지 전파되지 않는다.
 */
fun printHelloWorldWithSupervisorScope() = runBlocking {
    supervisorScope {
        launch {
            // supervisorScope 로 감싼 해당 {} 만 에러가 발생하고, 나머지는 영향을 끼치지 않음
            throw RuntimeException("Hello Error")
            delay(500)
            println("Hello")
        }
        launch {
            delay(100)
            println("World")
        }
    }

    launch {
        println("Done")
    }
}

val personalCoroutineScope = CoroutineScope(context = SupervisorJob())
val personalCoroutineScopeIO = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)

suspend fun printHelloWorldWithSupervisorScope2() = coroutineScope {

    delay(500)
    println("Do something...")

    // personalCoroutineScope.launch { notifyToUser() }
    // personalCoroutineScopeIO.launch { notifyToUser() }
    CoroutineScope(Dispatchers.IO).launch {
        // 에러가 부모코루틴으로 전파된다.
        notifyToUser()
    }

    println("Done")
}

// 별도 작업으로 처리할 수 있다.
suspend fun notifyToUser() {
    delay(3000)
    println("Hello World")
}

fun main() = runBlocking {
    printHelloWorldWithSupervisorScope2()
}