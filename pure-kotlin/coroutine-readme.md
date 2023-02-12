## [Coroutine basics](https://kotlinlang.org/docs/coroutines-basics.html)


---
### 1. [Your First Coroutine](https://kotlinlang.org/docs/coroutines-basics.html#your-first-coroutine)
* coroutine 은 인스턴스다. suspendable computation 인스턴스다.
  * `suspendable computation` 은 뭘까? -> `유예계산`
* coroutine 은 개념적으로는 thread 와 유사한데, 나머지 코드와 동시에 실행되기 위해서 필요하다.
  * `하지만` coroutine 은 thread 에 종속되지 않는다. ⭐
  * coroutine 은 thread 에 의해 중지(suspend) 될 수 있고, 또 다른 thread 에 의해 재개(resume) 될 수 있다.
* coroutine 은 경량 thread 로 생각할 수 있다.
  * `하지만` thread 와는 다르게 중요한 `차이점`이 존재한다.
```kotlin
// 코루틴을 사용 -> 정상동작
fun main() = runBlocking {
    repeat(10_000) {
      launch {
        delay(10000L)
        print(".")
    }
  }
}

// 스레드를 사용 -> oom 발생
fun main() {
  repeat(10_000) {
    thread {
      Thread.sleep(1000L)
      print(".")
    }
  }
}
```

---
* `runBlocking {}`
    * 코루틴 빌더로 선언했을 시 내부 블럭은 코루틴 스코프가 생긴다.
    * 현재 컨텍스트 내 실행되는 스레드는 내부의 코루틴을 수행이 완료될 때가지 block 된다.
        * 응용 프로그램 최상위에서 runBlocking 이 사용되는 것을 확인할 수 있다.
* launch {}
    * 코루틴 빌더이고 새로운 코루틴 스코프의 시작이다.
    * `단독으로 사용할 수 없고`, 동시성을 수행하기 위한 코루틴 스코프 내에서 설정되어야 한다.
* delay()
    * 코루틴 스코프 내에서 일시중지할 수 있는 특별한 함수
* structure concurrency ⭐⭐⭐
    * structure concurrency 란 코루틴 스코프 내에서 또 다른 코루틴 스코프를 만들 수 있는 것을 의미한다.

---  
### 2 [Extract function refactoring](https://kotlinlang.org/docs/coroutines-basics.html#extract-function-refactoring)
#### 2.1 `suspend`
* 코루틴 스코프 내에 있는 코드로직을 별도의 함수로 추출할 수 있다.
  * 그리고 해당 함수에 suspend 키워드를 붙일 수 있다.
  * 결과적으로 일반적인 함수에 suspend 키워드를 붙임으로써 해당 함수는 코루틴 블럭에서 사용할 수 있다.
* suspend 를 붙임으로써 비동기 실행을 위한 중단점으로 사용할 수 있다. 그리고 그 중단점은 다시 재개(resume) 된다.
* suspend function 는 `CoroutineScope` 에서 호출되거나 또는 `다른 suspend function` 으로부터 호출되어야 한다.


### 2.2 Scope Builder
* 코루틴 스코프는 다른 빌더를 제공하고 있다. coroutineScope 빌더를 사용해서 자신만의 빌더를 선언할 수 있다. 실행된 자식이 모두 실행될 때까지 완료되지 않는다.
* runBlocking 과 coroutineScope 는 자식이 끝날 때까지 기다리는 부분이 유사하다. 근데 차이점이 있다.
    * runBlocking 은 current thread 를 block 한다. current thread 가 waiting 하기 위해서.
    * coroutineScope 는 suspend (일시중지) 한다. 그리고 기본 스레드는 waiting 하지않고 다른 일을 수행한다.
    * 이러한 차이점으로 runBlocking 은 `일반함수` 이고 `coroutineScope` 는 `일시정지(suspend) 함수` 이다.
* coroutineScope 는 suspend function 에 같이 붙여서 쓸 수 있다.
* [CoroutineBuilderExample01.kt](./src/main/kotlin/coroutine/example01/CoroutineBuilderExample01.kt)

### 2.3 Scope Builder And Concurrency
* coroutineScope 빌더는 여러 작업을 `동시에` 처리하기 위해 내부적으로 여러 suspend function 을 이용할 수 있다.
* coroutineScope 빌더 안에 `동시에 실행시키고 싶은 내용들을 여러 launch {} 을 넣어 실행시킨다 : 동시성 수행 가능`
* [CoroutineBuilderExample02.kt](./src/main/kotlin/coroutine/example01/CoroutineBuilderExample02.kt)

### 2.4 An explicit job
* launch {} 코루틴 빌더는 job 을 반환한다.
* `val job = launch {}` 을 통해 받고, `job.join()` 을 수행하면 launch {} 을 포함한 내 자식 코루틴이 완료될 때까지 기다리게 된다.
* * [LaunchJobExample01.kt](./src/main/kotlin/coroutine/example02/LaunchJobExample01.kt)
* https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
* https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html

---
## 3. [cancellation-and-timeouts](https://kotlinlang.org/docs/cancellation-and-timeouts.html)
### 3.1 [Cancelling coroutine execution](https://kotlinlang.org/docs/cancellation-and-timeouts.html)
* 백그라운드로 돌아가는 coroutine 에 대해서 세밀한 컨트롤이 필요할 때가 있다.
* `val job = launch {}` 을 통해 job 을 전달받는다.
  * 전달받은 job 을 이후에 join() 시켜서 해당 코루틴(+ 자식 코루틴)이 완료될때까지 기다린다.
  * 이후에 cancel() 을 호출하여도 종료되지 않는다. -> join() 을 통해서 코루틴(+자식 코루틴) 이 완료될때까지 기다린다고 선언했기 때문에
  * `job.cancel() -> job.join() (= job.cancelAndJoin() 도 가능)` 순으로 호출하는 것은 취소가 완료될때까지 기디리는 것.
* [LaunchJobCancelExample01.kt.kt](./src/main/kotlin/coroutine/example02/LaunchJobCancelExample01.kt)

#### 3.1.1 왜? join() -> cancel() 을 먼저 호출하면 안될까?
* https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html
  * 위의 job 의 coroutineContext 를 살펴보면 join() 은 coroutineContext 의 `Completed` 가 될 때까지 대기함을 의미
  * 결과적으로 join() -> cancel() 순으로 하게되면, join() 이 먼저 호출되게 되서, `Completed` 상태가 강제되게 되는거 아닐까?
* https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/join.html

### 3.2 [Cancellation is cooperative](https://kotlinlang.org/docs/cancellation-and-timeouts.html#cancellation-is-cooperative)
* coroutine cancellation 은 cooperative 하다고 한다. (취소에 협조적이라고 함)
* kotlinx.coroutines 에 있는 suspend function 은 취소가 가능하다.
    * suspend function 은 coroutine 을 취소시키기 위해서 필요하다. coroutine scope 내에 `suspend` function 이 있어야 한다.
* 코루틴은 취소가 되는 경우에 `CancellationException` 을 throw 한다. 근데 코루틴 로직이 실행중이고 `Cancellation` 을 체크하지 않은 경우에 취소되지 않는다.
* 코루틴을 취소가능하게 하기
    * yield() 를 넣어준다. yield() 는 suspend function 인데 코루틴을 취소가능토록 해준다.
    * 명시적으로 상태를 체크하도록 한다. coroutine scope 내에 [isActive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/is-active.html) 를 넣어준다.
      * [LaunchJobCancelExample02.kt](./src/main/kotlin/coroutine/example02/LaunchJobCancelExample02.kt)
      * 코루틴 스코프 내에서 사용가능, 현재 코루틴 콘텍스트 내 job 활성여부를 체크.

### 3.3 [Closing resources with finally](https://kotlinlang.org/docs/cancellation-and-timeouts.html#closing-resources-with-finally)
* finally {} 를 통해 리소스를 닫을 수 있다.
  * launch {} 로 반환된 `job` 에 대해서 `job.cancelAndJoin()` 이 호출되더라도 finally 구문을 필히 실행된다.
  * [LaunchJobCancelFinallyExample01.kt](./src/main/kotlin/coroutine/example02/LaunchJobCancelFinallyExample01.kt)
  
### 3.4 [Run non-cancellable block](https://kotlinlang.org/docs/cancellation-and-timeouts.html#run-non-cancellable-block)
* finally {} 에서 별도 suspend function 를 만들 수 없다.
* `cancelAndJoin()` 을 수행하면 `CancellationException` 에러가 발생한다.
  * 해당 코드를 실행하는 코루틴이 취소되었기 때문. 
* 간혹 finally {} 에서 별도 suspend function 이 필요할 수 있다.
  * `withContext(NonCancellable)` 를 이용한다.
  * [LaunchJobCancelFinallyExample02.kt](./src/main/kotlin/coroutine/example02/LaunchJobCancelFinallyExample02.kt)
  * [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html)
    * 주어진 coroutineContext 가지고 suspending block 을 만든다.

---
## 4 [Timeout](https://kotlinlang.org/docs/cancellation-and-timeouts.html#timeout)
* 코루틴 실행을 취소하는 현실적인 방법은 제한시간이 초과되었을 때 취소하는 방법이다.
* withTimeout(1300L) 을 통해서 `TimeoutCancellationException` 에러가 발생한다.
  * `TimeoutCancellationException` 은 `CancellationException` 의 서브클래스이다.
* withTimeoutOrNull(1300L) 을 사용하면 익셉션이 발생하느 대신 null 을 반환한다.
  * [withTimeoutOrNull](https://kotlinlang.org/docs/cancellation-and-timeouts.html#timeout)

---
## 5. [Composing suspending functions](https://kotlinlang.org/docs/composing-suspending-functions.html)
* suspending functions 을 구성하는 여러방식들을 확인한다.

### 5.1 [Sequential by default](https://kotlinlang.org/docs/composing-suspending-functions.html#sequential-by-default)
* 순차적인 함수 호출을 통해서도 코루틴을 수행할 수 있다.
* 순차 실행이기 때문에 `시간이 오래걸림`

### 5.2 [Concurrent using async](https://kotlinlang.org/docs/composing-suspending-functions.html#concurrent-using-async)
* 순차적인 함수 호출이 아닌 동시에 수행하여 좀 더 빠르게 하고자 한다면 `async` 를 이용하여야 한다.
* `async` 는 개념적으로 `launch` 와 유사하다. 경량화된 스레드, 코루틴이 분리되는데 `다른 코루틴과 함께 동시에 실행` 된다.
* 차이점으로는
  * launch 는 job 을 반환 : 결과값 미존재.
  * async 는 deffered 를 반환 : 결과값 존재.
    * deffered 도 결국 job 의 일종, 취소가 가능하다.
    * deffered.await() 를 해서 결과값을 획득한다.
    * [CoroutineAsyncExample01.kt](./src/main/kotlin/coroutine/example03/CoroutineAsyncExample01.kt)

### 5.3 [Lazily started async](https://kotlinlang.org/docs/composing-suspending-functions.html#lazily-started-async)
* async 를 조금은 느리게 실행하는 방법
* `async(start = CoroutineStart.LAZY)` 를 작성하게 되면 비동기 코루틴을 조금 느리게 실행할 수 있다. deferred 는 이후에 start() 를 해주어야 한다.
* deferred.start() 가 없는 상태로, await() 만 설정하면 비동기 코루틴은 블락되어 순차적으로 수행된다.
* [CoroutineAsyncLazyExample01.kt](./src/main/kotlin/coroutine/example03/CoroutineAsyncLazyExample01.kt)
  * > 위 코드에서 one.start() 만 주석처리하고 two 는 넌블락이라 실행속도는 여전히 빠르다.

### 5.3 [Async-style functions](https://kotlinlang.org/docs/composing-suspending-functions.html#async-style-functions)
* 코틀린에서 하지 말라는 형태
    * async block 을 코루틴 블럭 외부에서 쓰지 말기. 별도의 함수로 추출해서 쓰지 말기. 그렇게 쓰면 익셉션 발생 시, 서브 코루틴들은 중간에 종료되지 못한다.
* GlobalScope block 안에 suspend 함수를 감싸지 말라.
  * [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html)

#### 5.3.1 GlobalScope (도큐먼트 해석)
* 어떤 잡에도 바인딩되지 않는다.
* Global scope 는 탑레벨 코루틴에 유용하게 사용된다.
  * Global scope 는 전체 애플리케이션 라이프타입으로 작동한다.
  * Global scope 는 취소할 수 없다.
* Global scope 에 실행된 코루틴은 프로세스 alive 를 유지하지 않는다. 마치 데몬 스레드와 같다.
  * Global scope 를 사용하면 리소스 낭비 또는 메모리 누수가 발생되기 쉽다.
  * [GlobalScopeExample01.kt](./src/main/kotlin/coroutine/example03/GlobalScopeExample01.kt)
* Global scope 를 쓰지 않으려면 어떻게 하는게 좋을까?
  * 대부분의 경우에는 `Global scope` 를 없애고 `suspend` 형태로 쓴다.
  * [GlobalScopeExample02.kt](./src/main/kotlin/coroutine/example03/GlobalScopeExample02.kt)
* top-level 코드에서 `suspend` 키워드 없이 동시에 작업을 해야하는 경우는 아래를 참고한다.
  * [GlobalScopeExample03.kt](./src/main/kotlin/coroutine/example03/GlobalScopeExample03.kt)
* Global scope 는 top-level application 단에서 동작할경우에는 유용하게 쓰일 수 있다. (이런 경우가 있으려나 : 스케줄러?)
  * 해당내용은 [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html) 도큐먼트를 확인한다.

### 5.4 [Structured concurrency with async](https://kotlinlang.org/docs/composing-suspending-functions.html#structured-concurrency-with-async)
* async 를 효율적으로 사용해야 한다.
* 위의 링크를 확인하면 coroutineScope 로 감싼 async block 내 에러가 발생 시, top-level catch 문에서 잡히는 걸 알 수 있다.

---
## 6. [Coroutine context and dispatchers](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html)
### 6.0.1 coroutine under the hood
> 코루틴은 마법이 아니다. 일반 코드처럼 실행된다.
* state machine (sm)
    * 현재의 호출 상태를 의미한다. `몇 번째 label 로 가야하는지` 정보도 여기 담긴다.

### 6.1 [Dispatchers and Thread](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#dispatchers-and-threads)
* 코루틴은 항상 코루틴 컨텍스트(coroutine-context) 내에서 실행된다.
* 코루틴 컨텍스트(coroutine-context) 는 코루틴 디스패처(dispatcher) 를 포함하고 있다.
  * 코루틴 디스패처는 해당 코루틴이 실행되는 스레드를 결정
  * [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) 
  * 코루틴 디스패처는 코루틴을 특정 스레드로 제한하거나 스레드풀에 디스패치하도록 할 수 있다.
  * 모든 코루틴 빌더(launch, async) 등은 코루틴 컨텍스트를 파라미터로 가지게 할 수 있다.
* `launch {}` 를 통해 여러개 콘텍스트 파라미터를 줄 수 있다.
  * `newSingleThreadContext` 이건 쓰지말자 : 매우 비싼 리소스가 소모됨

### 6.2 [Job in the context](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#job-in-the-context)
* 코루틴 의 job 은 context 의 일부이다.
* [JobInContextExample01.kt](./src/main/kotlin/coroutine/example04/JobInContextExample01.kt)

### 6.3 [Children of a coroutine](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#children-of-a-coroutine)
* 코루틴 다른 코루틴의 코루틴 스코프 내에서 시작되면, context 와 job 을 상속받는다.
  * 부모 코루틴이 취소가 되어버린다면, 모든 자식 코루틴도 취소가 된다.
* 부모와 자식 코루틴의 관계는 두가지 방식으로 재정의될 수 있다.
  * (1) 코루틴을 시작할 때 명시적으로 범위가 다른 경우 (ex. GlobalScope.launch {}), 이런 경우에는 부모 scope 로부터 job 을 상속받지 않는다.
  * (2) 다른 Job 객체가 서브 코루틴으로 전달되는 경우
  
### 6.4 [Parental responsibilities](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#parental-responsibilities)
* 부모 코루틴은 자식 코루틴이 모두 완료될 때까지 기다린다.
  * `job.join()` 을 쓴 경우에 해당한다.
    * 부모가 먼저 실행되더라도, 자식이 종료될때까지 기다린다.
    * [ParentCoroutineExample01.kt](./src/main/kotlin/coroutine/example04/ParentCoroutineExample01.kt)

### 6.5 [Coroutine scope](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#coroutine-scope)
* 코루틴 스코프를 이용해서 앱의 라이프사이클내에 코루틴 스코프를 관리할 수 있다.
    * 앱의 오퍼레이션이 다 끝나더라도 코루틴이 계속 동작한다면 메모리 누수가 발생할 수 있다.
    * 따라서 앱의 라이프 사이클에 맞게 앱이 종료되는 경우에 코루틴도 같이 중단시키면 좋다.
    * 코루틴 스코프는 이를 방지하게 한다. `cancel()` 을 통해서 더이상 코루틴 내 오퍼레이션이 동작하지 않도록 한다.
  * [CoroutineScopeExample01.kt](./src/main/kotlin/coroutine/example04/CoroutineScopeExample01.kt)

---
## 7. [Coroutine exceptions handling](https://kotlinlang.org/docs/exception-handling.html)
* 코루틴은 익셉션이 발생하면, [CancellationException](https://kotlinlang.org/docs/exception-handling.html) 을 발생시킨다.
* 코루틴 취소가 발생하면 어떤 일이 발생하는지 그리고 동일 코루틴에서 다수 자식 코루틴에게 익셉션이 발생하면 어떻게 되는지 살펴봐야 한다.

### 7.1 [Exception Propagation](https://kotlinlang.org/docs/exception-handling.html#exception-propagation)
* `launch {}` 와 `actor {}` 는 익셉션을 자동으로 전파한다.
* `async {}` 와 `produce {}` 는 익셉션을 사용자에게 노출한다.
* [ExceptionPropagationExample01.kt](./src/main/kotlin/coroutine/example05/ExceptionPropagationExample01.kt)

### 7.2 [CoroutineExceptionHandler](https://kotlinlang.org/docs/exception-handling.html#coroutineexceptionhandler)
* CoroutineExceptionHandler 를 통해서 코루틴 내 에러를 핸들링 할 수 있다.
* CoroutineExceptionHandler 를 만들고, 해당 블럭 내에서 익셉션을 전달받아 후처리를 할 수 있도록 한다.
* [ExceptionPropagationExample02.kt](./src/main/kotlin/coroutine/example05/ExceptionPropagationExample02.kt)

### 7.3 [Cancellation and exception](https://kotlinlang.org/docs/exception-handling.html#cancellation-and-exceptions)
* Cancellation 은 exception 과 연관이 있다.
* Coroutine 은 내부적으로 취소건에 대해 CancellationException 을 사용한다.
* 자식 코루틴이 모두 끝나야, 이후에 부모 코루틴이 취소된다.
* [CancellationExceptionExample01.kt](./src/main/kotlin/coroutine/example05/CancellationExceptionExample01.kt)

### 7.4 [Exception Aggregation](https://kotlinlang.org/docs/exception-handling.html#exceptions-aggregation)
* 코루틴 내 여러개 자식 코루틴이 익셉션 떨어질 때, 기본적인 규칙은 `첫번째 익셉션이 우선이다.` 원칙이다.
  * 따라서 첫번째 익셉션이 핸들링 되면, 나머지 익셉션은 첫번째 익셉션에 억제된 예외로 첨부된다.

---
## 8. [Asynchronous Flow](https://kotlinlang.org/docs/flow.html)
* suspend 함수는 비동기적으로 단일 값을 반환한다. 하지만 비동기 연산에 의해서 멀티 값을 반환은 어떻게 할 것인가?

### 8.1 [Representing multiple values : Sequence](https://kotlinlang.org/docs/flow.html#sequences)
* 여러 개의 값은 코틀린에서 collections 를 사용해서 나타낼 수 있다.
* 만약 일부 CPU 를 사용하는 블럭킹 코드를 계산하겠다고 한다면, 100ms 가 걸리는 처리건에 대해서 `sequence` 를 이용해서 처리할 수 있다.
  * 각각 별도로 처리되고 해당 결과가 별도로 반환된다.
* [FlowExample01.kt](./src/main/kotlin/coroutine/example06/FlowExample01.kt)

### 8.2 [Suspending Function](https://kotlinlang.org/docs/flow.html#suspending-functions)
* 계산은 코드를 실행하는 주 스레드를 차단한다.
* 이런 값이 비동기 코드로 계산될 때, suspend 키워드를 가지고 간단한 함수를 표시할 수 있고 별도 블럭킹 없이 작업을 수행하고 결과목록을 반환할 수 있다.
* [FlowExample02.kt](./src/main/kotlin/coroutine/example06/FlowExample02.kt)

### 8.3 [Flow](https://kotlinlang.org/docs/flow.html#flows)
* List<Int> 를 사용하면, 딱 한번 모든 값을 반환한다.
* 값에 대한 비동기적인 계산이 되려면, Flow<Int> 를 사용한다.
* Sequence<Int> 는 동기식 계산된 값에 사용하는 것이 유용하다.
* [FlowExample03.kt](./src/main/kotlin/coroutine/example06/FlowExample03.kt)
  * Flow builder function 에서의 타입은 flow 타입이다.
  * `flow { }` 빌더 블럭은 일시중지(suspend) 될 수 있다.
  * simpleFlow() 에 suspend 키워드를 붙여주지 않아도 된다.
  * 값들은 `emitted` 된다. : flow {} 에서 `emit` 함수를 통해서 된다.
  * 값들은 `collected`  된다 : flow {} 에서 `collect` 함수를 통해서 된다.

### 8.4 [Flows are cold](https://kotlinlang.org/docs/flow.html#flows-are-cold)
* Flows 는 Sequences 와 유사한 콜드스트림이다.
* flow builder 수행되지 않는다. flow collected 가 되기 전까지.
  * Flow<T>.collect() 가 호출되어야 한다.
* [FlowExample04.kt](./src/main/kotlin/coroutine/example06/FlowExample04.kt)
* 위에서 출력되는 주요 이유는 `coldStreamFlow()` 이 suspend 로 처리되어있지 않기 때문이다.
  * `coldStreamFlow()` 는 빠르게 값을 반환하고, 어떤 것도 기다리지 않는다.
  * flow 는 매시간 collected 처리될 수 있다. 해당 부분이 `collect` 를 재호출하면 Flow started 가 출력되는 이유다. 

### Flow Builder(https://kotlinlang.org/docs/flow.html#flow-builders)
* `flow { ... }` builder 는 가장 기본적인 플로우 빌더다. flow { } builder 형태로 선언하기 쉬운 다른 빌더들도 살펴본다.
    * `flowOf` builder
    * `asFlow()` builder

### 100. [TIP](#)
* 코루틴 스코프 내의 코루틴 이름을 알고 싶다면 intellij vm option 을 `-Dkotlinx.coroutines.debug` 로 준다
    * `println(Thread.currentThread.name())` 으로 작성한다

## reference
* https://kotlinlang.org/docs/coroutines-overview.html
* [인프런 코루틴 무료 강의](https://www.inflearn.com/course/%EC%83%88%EC%B0%A8%EC%9B%90-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BD%94%EB%A3%A8%ED%8B%B4/dashboard)
* [kotlin flow api 살펴보기 naver d2](https://youtu.be/D8rUDoYCZlo)
* https://proandroiddev.com/structured-concurrency-in-action-97c749a8f755
* [Introduction to Kotlin coroutine](https://youtu.be/VPTcj1mU-5c)
* [Difference between a "coroutine" and a "thread"?](https://stackoverflow.com/questions/1934715/difference-between-a-coroutine-and-a-thread)