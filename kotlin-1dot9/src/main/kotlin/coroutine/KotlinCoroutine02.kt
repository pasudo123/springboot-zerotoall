package coroutine

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

val COROUTINE_SUSPENDED = Any()

fun myFunctionCustom(paramContinuation: Continuation<Unit>): Any {
    val continuation = paramContinuation as? MyFunctionContinuation ?: MyFunctionContinuation(paramContinuation)

    if (continuation.label == 0) {
        println("Before")
        if (cusomDelay(1000, continuation) == COROUTINE_SUSPENDED) {
            return COROUTINE_SUSPENDED
        }
    }

    if (continuation.label == 1) {
        println("After")
        return Unit
    }

    error("Error")
}

fun cusomDelay(timeMilli: Long, continuation: MyFunctionContinuation): Any {
    continuation.label = 1
    continuation.continuationToResume = continuation
    Thread {
        Thread.sleep(timeMilli)
        continuation.resume(Unit)
    }.start()
    return COROUTINE_SUSPENDED
}

class MyFunctionContinuation(
    val completion: Continuation<Unit>,
) : Continuation<Unit> {

    override val context: CoroutineContext
        // 내가 수정한 부분.
        get() = EmptyCoroutineContext

    var label = 0
    var result: Result<Any>? = null
    var continuationToResume: Continuation<Unit>? = null

    override fun resumeWith(result: Result<Unit>) {
        this.result = result
        val response = try {
            val current = myFunctionCustom(this)
            if (current == COROUTINE_SUSPENDED) return
            Result.success(current as Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }

        completion.resumeWith(response)
    }
}