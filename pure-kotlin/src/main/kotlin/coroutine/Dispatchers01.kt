package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#2] : main runBlocking
[main @coroutine#3] : Unconfined
[DefaultDispatcher-worker-1 @coroutine#4] : Default
[MyOwnThread @coroutine#5] : MyOwnThread
[DefaultDispatcher-worker-1 @coroutine#6] : IO
 */
class Dispatchers01 {
    fun main() = runBlocking<Unit> {

        // 메인스레드 실행된다.
        // launch 에 아무것도 정의되지 않으면 상위 컨텍스트를 상속받는다.
        launch {
            printlnWithThreadName("main runBlocking")
        }

        // 메인스레드 실행되는것 처럼 보이지만 약간 다른 메커니즘을 가지고 있다.
        launch(Dispatchers.Unconfined) {
            printlnWithThreadName("Unconfined")
        }

        // Default 디스패처에서 실행된다. : 스레드풀에서 스레드 자원을 공유해서 사용한다.
        launch(Dispatchers.Default) {
            printlnWithThreadName("Default")
        }

        // 코루틴이 사용할 스레드를 생성해서 이용한다. : 비용적으로 비싸다. (실제로 이렇게 쓰면 안됨)
        launch(newSingleThreadContext("MyOwnThread")) {
            printlnWithThreadName("MyOwnThread")
        }

        launch(Dispatchers.IO) {
            printlnWithThreadName("IO")
        }
    }
}