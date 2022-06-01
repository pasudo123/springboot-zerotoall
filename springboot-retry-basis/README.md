## springboot-retry-basis
springboot 에서 retry 를 수행

## 기본 retry 전략
```kotlin
@Service
class CoffeeService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable
    fun insertCoffee() {
        val num = Random.nextLong(10)
        if (num < 5) {
            log.error("force error !!")
            throw RuntimeException("$num 으로 인한 에러 발생")
        }

        log.info("success")
    }
}
```
* 메소드 레벨에서 붙일 수 있음
* 1초간격으로 3번 재시도 수행
* @Retryable(value = [RuntimeException::class]) 형태로 특정한 익셉션에 반응하도록 설정가능

## 기본 retry 전략 + recover 
```kotlin
@Retryable
@Throws(exceptionClasses = [CoffeeInsertException::class])
fun insertCoffeeWithRecover(
    firstMessage: String,
    secondMessage: String? = null
) {
    val num = Random.nextLong(10)
    if (num < 9) {
        log.error("force error !!")
        throw CoffeeInsertException("$num 으로 인한 에러 발생")
    }

    log.info("success")
}

@Recover
private fun recoverByInsertCoffee(
    exception: CoffeeInsertException,
    firstMessage: String,
    secondMessage: String? = null

) {
    val lines = StringBuilder().apply {
        this.appendLine()
        this.appendLine("[retry recover]")
        this.appendLine("firstMessage : $firstMessage")
        this.appendLine("secondMessage : $secondMessage")
    }

    log.info(lines.toString())
}
```
* 메소드 레벨에서 붙이고 이후에 retry 까지 모두 수행했음에도 불구하고 실패된 경우 리커버리를 수행한다.
* `위의 코드처럼` 첫번째 파람으로 익셉션 인자를 받고, 두번째는 @Retryable 의 메소드(`insertCoffeeWithRecover`) 인자를 순서대로 받는다.
* @Recover 에서 에러가 발생하는 경우? -> 그냥 익셉션이 일어난다. 이건 그 앞단에서 try catch 로 잡아서 별도 처리해줘야 할듯?

## retryTemplate 이용
```kotlin
@Bean
fun retryTemplate(): RetryTemplate {
    val fixedBackOffPolicy = FixedBackOffPolicy().apply {
        this.backOffPeriod = 3000L
    }

    val retryPolicy = SimpleRetryPolicy().apply {
        this.maxAttempts = 2
    }

    val retryTemplate = RetryTemplate().apply {
        this.setBackOffPolicy(fixedBackOffPolicy)
        this.setRetryPolicy(retryPolicy)
    }

    return retryTemplate
}
```
* 별도 retryTemplate 을 빈으로 등록한다.
    * 시도횟수 및 재시도간 시간을 설정

```kotlin
fun insertCoffeeWithRetryTemplate() {
    retryTemplate.execute(RetryCallback<Void, CoffeeInsertException> { context ->
        this.insertCoffee()
        null
    })
}

private fun insertCoffee() {

    log.info("insertCoffee() called...")

    val num = Random.nextLong(10)
    if (num < 9) {
        log.error("force error !!")
        throw CoffeeInsertException("$num 으로 인한 에러 발생")
    }
}
```
* 로직을 수행하는 부분을 retryTemplate callback 으로 한번 래핑한다. 그리고 클라이언트는 래핑한 메소드를 호출한다.
  * insertCoffeeWithRetryTemplate() 를 호출
* @Recover, 리커버리는 설정이 되어있지 않기 때문에, maxAttempts 를 초과하면 그대로 에러가 발생한다.

## RetryListenerSupport 사용
```kotlin
class DefaultListenerSupport : RetryListenerSupport() {

  private val log = LoggerFactory.getLogger(javaClass)

  /**
   * (1-1) retryTemplate 메소드가 호출되는 시점에 호출
   * (2-1) @Retryable 메소드가 호출되는 시점에 호출
   */
  override fun <T, E : Throwable?> open(context: RetryContext?, callback: RetryCallback<T, E>?): Boolean {
    log.info("==> open")
    return super.open(context, callback)
  }

  /**
   * (1-2) retryTemplate 메소드 내에 에러가 발생하는 시점에 호출
   * (2-2) @Retryable 이 붙은 메소드에서 에러가 발생할 때 호출
   */
  override fun <T, E : Throwable?> onError(
    context: RetryContext?, callback: RetryCallback<T, E>?,
    throwable: Throwable?
  ) {
    log.info("==> onError")
    super.onError(context, callback, throwable)
  }

  /**
   * (1-3) retryTemplate 메소드가 최대 시도까지 완료한 시점에 호출 (성공/실패 여부 상관없이)
   * (2-3) @Retryable 메소드가 최대 시도(혹은 리커버리) 까지 완료한 시점에 호출 (성공/실패 여부 상관없이)
   */
  override fun <T, E : Throwable?> close(
    context: RetryContext?, callback: RetryCallback<T, E>?,
    throwable: Throwable?
  ) {
    log.info("==> close")
    super.close(context, callback, throwable)
  }
}
```
* 위와 같이 `RetryListenerSupport` 를 구현한다.
* retryTemplate.setListeners() 을 통해서 등록할 수 있다.
* @Retryable 의 attr 값으로 등록할 수 있다. (대신 빈으로 등록이 되어야 함)

## 고려사항
* retry 수행시에, 대상 서버가 회복할 시간을 주는 것이 좋다.
  * 짧은 지연시간의 retry 요청은 대상서버에 부하를 줄 수 있다.
  * 대상서버가 클라이언트로부터 받는 요청이 나 뿐만 아니라 다른 클라이언트 서버들도 있을 수 있다는 사실음 염두한다.
* api 재시도 처리또한 대상 서버가 여러번 중복호출 될 수 있다는 사실을 염두에 둔다.

## 참고
* https://www.baeldung.com/spring-retry
* https://daddyprogrammer.org/post/12091/spring-retry-review/
* https://brunch.co.kr/@springboot/580