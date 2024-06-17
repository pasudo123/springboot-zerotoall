package coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KotlinCoroutine06

fun main() {
    runBlocking {
        coroutineScopeFunc()
        runBlockingFunc()
    }
}

// 코루틴 빌더를 사용하기 위해서, coroutineScope 로 wrapping
suspend fun coroutineScopeFunc() = coroutineScope {
    launch { println("hello") }
}

// 코루틴 빌더를 사용하기 위해서, runBlocking 로 wrapping
suspend fun runBlockingFunc() = runBlocking {
    launch { println("world") }
}

/**
 * 코루틴 빌더를 사용하기 위해선, 코루틴 스코프 혹은 runBlocking 이 필요하다.
 * 없다면 컴파일 에러가 발생한다. (아래 코드는 컴파일 에러가 발생하는 코드이다.)
 */
//suspend fun basicFunc() {
//    launch {  } -> 코루틴 빌더(launch or await) 를 사용할 수 없다. 컴파일 에러 발생.
//}