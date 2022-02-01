package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#1] : Hello
[main @coroutine#2] : World !!
 */
class Basis02 {
    fun main() = runBlocking {
        launch { doWorld() }
        printlnWithThreadName("Hello")
    }

    // launch {} 내 코드를 함수로 추출하고 분리했다.
    // 그리고 새로운 함수 doWorld() 앞에 suspend 라는 키워드 붙여줘야 한다.
    suspend fun doWorld() {
        delay(2000L)
        printlnWithThreadName("World !!")
    }
}