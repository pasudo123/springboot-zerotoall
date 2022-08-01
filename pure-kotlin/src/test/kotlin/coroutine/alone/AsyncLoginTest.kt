package coroutine.alone

import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AsyncLoginTest {

    @Test
    @DisplayName("테스트코드")
    fun asyncLogicTest() = runBlocking {

        println("process 1")

        async(Dispatchers.IO) {
            unlock()
        }

        println("process 2")

        delay(350)

        println("process 3")
    }

    private suspend fun unlock() = coroutineScope {
        delay(300)
        println("unlock")
    }
}