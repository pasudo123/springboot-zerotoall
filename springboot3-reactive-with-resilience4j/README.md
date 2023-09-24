## springboot3 reactive with resilience4j project
- 스프링부트3와 리액티브, resilience4j 적용샘플

## resilience4j-circuitbreaker
```yml
resilience4j:
  circuitbreaker:
    instances:
      coffeeService:
        registerHealthIndicator: true   # 액츄에이터 /actuator/health 에 서킷브레이커를 노출하기 위함, false 면 미노출
        slidingWindowType: TIME_BASED   # 시간단위로 faliure/slow rate 를 집계. COUNT_BASED 도 존재
        slidingWindowSize: 10           # slidingWindowType 에 맞춰 ${slidingWindowSize} 초단위 내에서 발생건수를 집계함을 의미  
        minimumNumberOfCalls: 10        # error/slow rate 집계 시, 최소 call 수. 해당 call 수를 크거나 같을떄 집계가 됨
        failureRateThreshold: 50        # error rate 임계치 (퍼센트)
        permittedNumberOfCallsInHalfOpenState: 3    # HALF_OPEN 상태 시 허용되는 call 수, 해당 결과에 따라 실패율을 집계하고 다시 CLOSE/OPEN 전이가 결졍된다
        waitDurationInOpenState: 15s    # OPEN -> HALF_OPEN 으로 상태 트랜지션이 전이될 때의 대기시간
        automatic-transition-from-open-to-half-open-enabled: true # 별도 스레드가 돌아서 ${waitDurationInOpenState} 만큼 시간이 자니면 OPEN -> HALF_OPEN 으로 상태전이를 시켜줌 
        record-exceptions:              # error/slow rate 로 기록되는 익셉션
          - com.example.springboot3reactivewithresilience4j.circuitbreaker.CoffeeRecordException
        ignore-exceptions:              # error/slow rate 에 기록이 무시되는 익셉션
          - com.example.springboot3reactivewithresilience4j.circuitbreaker.CoffeeIgnoreException
```
* failureRate = `numberOfFailedCalls` / `numberOfBufferedCalls`
  * failureRate >= failureRateThreshold 로 된다면 OPEN 상태로 전이
* OPEN 상태에서도 최소 1번 호출되고, waitDurationInOpenState 시간만큼 계속 fallback 처리 하다가 이후에 HALF_OPEN 으로 전이
* 만약 `automatic-transition-from-open-to-half-open-enabled` 값이 true 면 최소 1번 호출 없이 자동으로 OPEN -> HALF_OPEN 으로 전이
  * `automatic-transition-from-open-to-half-open-enabled` 가 true 면 서킷 뒷단에 별도 스레드가 작동한다. (https://resilience4j.readme.io/docs/circuitbreaker)
* HALF_OPEN 상태에서 호출 시, permittedNumberOfCallsInHalfOpenState 만큼 호출되고 그 집계가 failureRateThreshold 보다 크거나 같다면 OPEN, 그렇지 않다면 CLOSE 로 상태전이

## resilience4j-timelimiter
* 주어진 메소드나 함수의 실행시간을 제한하는데 사용.
* exception 을 throw 하여도 FallbackMethod 설정 시 작동된다.
```yml
resilience4j:
  timelimiter:
    instances:
      snackService:
        cancelRunningFuture: false  # 실행중인 작업을 취소할지 여부 (리턴타입이 Future 인 경우에 해당)
        timeoutDuration: 2s         # 얼만큼 시간을 제한할건지
```

## resilience4j pattern 우선순위
resilience4j 의 우선순위가 존재한다. 필요에 따라 yml 에서 변경이 가능하다.
- 1순위, resilience4j.bulkhead.bulkheadAspectOrder
- 2순위, resilience4j.timelimiter.timeLimiterAspectOrder
- 3순위, resilience4j.ratelimiter.rateLimiterAspectOrder
- 4순위, resilience4j.circuitbreaker.circuitBreakerAspectOrder
- 5순위, resilience4j.retry.retryAspectOrder

## resilience4j module, cache
- 공식문서에선 JCache 를 래핑해서 쓰고 있는데, 프로덕션 환경에선 사용을 추천하지 않는다 (동시성 이슈가 있는듯)
- 따라서 다른 캐시를 쓰도록 권장한다. ex) Ehcache, Caffenine, Redisson, etc
- https://resilience4j.readme.io/docs/cache#ehcache-example

## 참고
* https://resilience4j.readme.io/
