package coroutine.example05

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ExceptionPropagationExample02

fun main() = runBlocking {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("custom handler : $exception")
    }

    // launch 는 에러를 전파한다.
    val jobV1 = GlobalScope.launch(handler) {
        throw AssertionError("assert error-1 !!")
    }

    val jobV2 = GlobalScope.launch(handler) {
        throw IndexOutOfBoundsException("indexOutOfBounds error-2 !!")
    }

    // async 는 에러발생시 사용자에게 노출
    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException("arithmetic error!!")
    }

    // job 만 여기서 호출된다.
    joinAll(jobV1, jobV2, deferred)

    try {
        deferred.await()
    } catch (exception: Exception) {
        println("catch async error !!")
    }
}