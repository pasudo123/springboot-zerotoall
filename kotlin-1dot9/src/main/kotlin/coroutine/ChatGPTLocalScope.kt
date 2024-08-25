package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChatGPTLocalScope

/**
 * 아래코드는 권장되는 방식이 아니다.
 * 차라리 하려면 구조화된 코루틴 형식으로 바꿔야한다.
 */
fun mainLocalScope() = runBlocking {
    val localScope = CoroutineScope(Dispatchers.IO)

    localScope.launch {
        delay(1000)
        println("hello world")
    }

    /**
     * join() 을 통해서 기다린다. 메인스레드가 block 된다.
     * join() 이 없다면 메인스레드가 block 되지 않는다.
     */
    localScope.coroutineContext[Job]?.join()

    println("done")
}