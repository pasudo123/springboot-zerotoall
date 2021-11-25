# springboot-testcode-basis

## 여기서 다룰 것
* [Kotlin 에서 테스트코드를 잘 짜는법](https://phauer.com/2018/best-practices-unit-testing-kotlin)
* @DataJpaTest 하기
* @MockMvcTest 하기
  * response 응답 필드내, 한글인코딩 문제 해결하기
* @SpringBootTest 하기
* @EnableJpaAuditing 을 테스트코드해서 특정 시간대로 넣어보기
  * DateTimeProvider 를 조작한다.

## 테스트코드를를 위한 테스트 메타 애노테이션 작성
```kotlin
/**
 * 테스트 관련 메타 애노테이션에서 사용하기 위한 최상위 애노테이션
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
annotation class TestEnvironment


/**
 * @Controller, @Service, @Repository 를 TestContext 에 띄어놓고 테스트하기 위한 메타애노테이션
 * - 각 테스트 컨텍스트마다 테스트 격리를 위한 @Transactional 을 붙여준다.
 * - TestRestTemplate 도 별도로 사용이 가능한다.
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = [TestObjectMapperConfiguration::class])
annotation class IntegrationSupport


/**
 * @Controller 계층만 테스트하기 위한 메타애노테이션
 * - 나머지 레이어 계층 영역은 mocking 해주어야 함
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@WebMvcTest
@Import(value = [TestObjectMapperConfiguration::class])
annotation class WebLayerSupport


/**
 * @Repository 를 테스트하기 위한 메타애노테이션
 * - 자동롤백된다.
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@DataJpaTest
@Import(JpaAuditingBaseConfiguration::class)
annotation class RepositorySupport


/**
 * @Controller, @Service, @Repository 를 TestContext 에 띄어놓고 테스트하기 위한 메타애노테이션
 * - 디스패처 서블릿 단위까지 테스트가 가능하기 때문에 필터, 인터셉터까지 확인할 수 있다.
 * - 각 테스트 컨텍스트마다 테스트 격리를 위한 @Transactional 을 붙여준다.
 * - @AutoConfigureMockMvc 를 통해서 MockMvc 에 대한 의존성을 받는다.
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@Import(value = [
    JpaAuditingBaseConfiguration::class,
    TestObjectMapperConfiguration::class
])
annotation class MockMvcSupport


/**
 * 단순 mock 객체만을 만들어서 테스트하기 위한 메타애노테이션
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(JpaAuditingBaseConfiguration::class)
annotation class SimpleMockSupport

```