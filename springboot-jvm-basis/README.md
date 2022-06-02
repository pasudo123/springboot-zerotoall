## springboot-jvm-basis

## pass arguments 이용, JVM 옵션 전달
```shell
$ ./gradlew bootRun -Dspring-boot.run.arguments="--spring.profiles.active=cli,--spring.main.banner-mode=off"
```
* `-Dspring-boot:run.arguments` 형태로 스프링 내에서 쓰이는 인자들을 넘길 수 있다.
* 

## build.gradle.kts 를 통해서 bootRun 실행 시, 힙메모리 설정
```shell
tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    println("[bootRun] START @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

    // args 리스트 형태로 전달
    args("--spring.profiles.active=kts", "--spring.main.banner-mode=off")

    // JVM 구성 최적화를 끈다.
    isOptimizedLaunch = false

    // jvmArguments
    jvmArgs = listOf(
        "-Xms3072m",
        "-Xmx3072m",
        "-XX:MaxPermSize=256m",
        "-XX:MaxMetaspaceSize=512m",
        "-XX:+HeapDumpOnOutOfMemoryError",
        "-Dfile.encoding=UTF-8"
    )

    println("[bootRun] END   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
}
```

## jvm args 옵션들


## 참고자료
* https://www.baeldung.com/spring-boot-heap-size
* https://www.baeldung.com/spring-boot-command-line-arguments