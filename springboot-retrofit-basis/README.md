# retrofit 익히기.
내맘대로 필요한 것만 익힌다.

## 2. suspendCancellableCoroutine, [링크](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/suspend-cancellable-coroutine.html)
```kotlin
suspend fun <T> Call<T>.awaitResponse(): Response<T> {
  return suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
      cancel()
    }
    enqueue(object : Callback<T> {
      override fun onResponse(call: Call<T>, response: Response<T>) {
        continuation.resume(response)
      }

      override fun onFailure(call: Call<T>, t: Throwable) {
        continuation.resumeWithException(t)
      }
    })
  }
}
```
* suspendCoroutine 에 CancellableContinuation 을 제공
* 일시중지된 상태에서 작업이 완료되거나, 취소되면 CancellationException 을 발생
* `single-shot callback` 에서 결과를 기다리는 동안 코루틴을 중지할 때 해당 함수는 사용된다. 그리고 caller 의 결과를 반환할 때 사용된다.
* resume() { } 은  

## kotlinx-coroutine-reactor [링크](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactor/index.html)

## reference
* [coroutine 과 continuation 이 차이는 무엇인가요?](https://stackoverflow.com/questions/50598519/in-kotlin-whats-the-difference-between-the-terms-coroutine-and-continuation/50598570)