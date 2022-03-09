## 확인사항
* @DataJpaTest 하기
* @MockMvcTest 하기
    * response 응답 필드내, 한글인코딩 문제 해결하기
* @SpringBootTest 하기
* @EnableJpaAuditing 을 테스트코드해서 특정 시간대로 넣어보기
    * DateTimeProvider 를 조작한다.
* SpringBoot 에서 테스트코드를 위한 다양한 애노테이션을 메타애노테이션으로 관리하기
* SpringBoot 에서 테스트컨텍스트 내 공유하는 데이터베이스 자원을 매 테스트 하기 이전에/이후에 deleteAll 또는 truncate (drop & create) 하기
  * `h2 이용`
* SpringBoot 에서 테스트컨텍스트 내 공유하는 데이터베이스 자원을 매 테스트 하기 이전에/이후에 deleteAll 또는 truncate (drop & create) 하기
  * `테스트 컨테이너 이용`
  * `테스트 컨테이너 mysql 작성`

## 테스트코드를 위한 테스트 메타 애노테이션 작성
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
 *   - TestRestTemplate 은 별도의 서블릿 컨테이너 내에서 실행, 요청당 별도의 스레드가 만들어진다.
 *   - TestRestTemplate 으로 테스트코드가 있을 시, 트랜잭션 롤백이 되지 않음
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = [BaseTestConfiguration::class])
annotation class IntegrationSupport


/**
 * @IntegrationSupport 에 @Transactional 이 적용되지 않은 상태
 * - TruncateDbSupport 유무에 따라 테스트 성공/실패 여부가 결정된다.
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TruncateDbSupport(truncateCycle = TruncateCycle.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = [BaseTestConfiguration::class])
annotation class IntegrationSupportWithTruncateDb


/**
 * @IntegrationSupport 에 @Transactional 이 적용되지 않은 상태 + TestContainer 포함된 상태
 * - TruncateDbSupport 유무에 따라 테스트 성공/실패 여부가 결정된다.
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TruncateDbSupport(truncateCycle = TruncateCycle.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [BaseTestConfiguration::class, BaseTestContainerConfiguration::class])
@ActiveProfiles("testcontainer")
annotation class IntegrationSupportWithTestContainers


/**
 * @Controller 계층만 테스트하기 위한 메타애노테이션
 * - 나머지 레이어 계층 영역은 mocking 해주어야 함
 */
@TestEnvironment
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@WebMvcTest
@Import(value = [BaseTestConfiguration::class])
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
  BaseTestConfiguration::class
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


/**
 * test context 내 공유된 데이터를 초기화한다. : 테스트 격리를 위함
 * BeforeEach 혹은 AfterEach 에서 truncate 가 동작될 수 있도록 한다.
 */
@ExtendWith(value = [TruncateDbExtension::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TruncateDbSupport(
  val truncateCycle: TruncateCycle = TruncateCycle.AFTER_TEST_METHOD
)

/**
 * TODO(Cycle 에 의해서 BeforeEach 혹은 AfterEach 에 진행할 것인지 결정 필요)
 */
enum class TruncateCycle {
  BEFORE_TEST_METHOD,
  AFTER_TEST_METHOD
}
```