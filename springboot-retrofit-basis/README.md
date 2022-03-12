# 0 retrofit 익히기.
내맘대로 필요한 것만 익힌다.

---
## 1.1 retrofit 인터페이스 함수에 suspend 을 붙일까 말까?
```kotlin
interface ShortNewsClient {

    @GET("news")
    fun getNewsByCategory(@Query("category") category: String): Call<ShortNewsResponse>

    @GET("news")
    suspend fun getNewsByCategory(@Query("category") category: String): ShortNewsResponse
}
```
* suspend 를 붙일 수 있지만 그렇게 되면 `Call` 키워드를 제거해주어야 함
  * Call 키워드를 붙여주면, Call 생성자 에러가 떨어짐 
  * 추가적으로 에러 핸들링을 하기에 한번더 호출객체에서 try catch 문으로 감싸주어야 하는 문제가 있다.
* suspend 를 안 붙이고 retrofit 의 kotlin-extension 을 이용하는데, 코드 이용면 측에서 더 좋아보임.
  * `.await()`, `.awaitResponse()` 등

---
## 2.1 suspendCancellableCoroutine, [링크](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/suspend-cancellable-coroutine.html)
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

## 2.2 kotlinx-coroutine-reactor, [링크](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-reactor/index.html)

## reference
* [coroutine 과 continuation 이 차이는 무엇인가요?](https://stackoverflow.com/questions/50598519/in-kotlin-whats-the-difference-between-the-terms-coroutine-and-continuation/50598570)
* [spring-framework-coroutine](https://docs.spring.io/spring-framework/docs/5.2.0.M1/spring-framework-reference/languages.html#coroutines)