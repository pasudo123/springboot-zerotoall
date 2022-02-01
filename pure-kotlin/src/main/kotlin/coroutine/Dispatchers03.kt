package coroutine

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

/**
[Ctx1 @coroutine#2] Started in ctx1
[Ctx2 @coroutine#2] Working in ctx2
[Ctx1 @coroutine#2] Back to ctx1
 */
class Dispatchers03 {
    fun main() = runBlocking {

        // 스레드를 2개 만든 상태에서, 동일 runBlocking 내 코루틴은 스레드를 넘나는다.
        // Ctx1 -> Ctx2 -> Ctx1
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    log("Started in ctx1")
                    withContext(ctx2) {
                        log("Working in ctx2")
                    }
                    log("Back to ctx1")
                }
            }
        }
    }
}