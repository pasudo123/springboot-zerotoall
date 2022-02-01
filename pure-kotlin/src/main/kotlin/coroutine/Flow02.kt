package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#2] : I'm not blocked 1
[main @coroutine#1] : current : 1
[main @coroutine#2] : I'm not blocked 2
[main @coroutine#1] : current : 2
[main @coroutine#2] : I'm not blocked 3
[main @coroutine#1] : current : 3
 메인스레드가 블락킹 없이 실행되는 것을 확인할 수 있다.
 */
class Flow02 {
    // flow block 에서 별도의 블럭을 처리할 필요가 없다.
    // delay 를 Thread.sleep 으로 바꾸게 되면, simple 함수는 메인스레드가 차단되어 1, 2, 3, 을 먼저 출력한다.
    // 그리고 이후 메인스레드 내 launch {} 내 코루틴이 실행된다,
    private fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100L)
//            Thread.sleep(100L)
            emit(i)
        }
    }
    fun main() = runBlocking {
        launch {
            for (k in 1..3) {
                printlnWithThreadName("I'm not blocked $k")
                delay(100L)
            }
        }

        simple().collect { value -> printlnWithThreadName("current : $value") }
    }
}

