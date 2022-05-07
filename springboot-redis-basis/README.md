## springboot-redis-basis

## redisTemplate 을 래핑한 repository 클래스 테스트하기
* @Repository annotation 을 부착한 클래스에 대해서 테스트를 할 수 있도록 한다.
* 레디스 인프라는 별도 임베디드 레디스를 쓰지 않고, 레디스 컨테이너를 이용할 수 있도록 한다.
```kotlin
/**
 * RedisTemplate 를 wrapping 한 레파지토리 클래스(@Repository)에 대한 테스트가 가능하다.
 * - @Import / @ContextConfiguration 을 구분해서 써야함.
 */
@TestEnvironment
@DataRedisTest(
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [Repository::class]
        )
    ]
)
@ContextConfiguration(classes = [CustomRedisTestConfiguration::class])
@Import(value = [CustomRedisContainer::class])
annotation class RedisRepositorySupport
```