## springboot-jvm-basis

## pass arguments 이용, JVM 옵션 전달
```shell
$ ./gradlew bootRun -Dspring-boot.run.arguments="--spring.profiles.active=cli,--spring.main.banner-mode=off"
```
* `-Dspring-boot:run.arguments` 형태로 스프링 내에서 쓰이는 인자들을 넘길 수 있다.

```shell
$ ./gradlew bootRun
```
* 단순하게 실행할 수 있다.

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
        "-XX:+UseSerialGC"
    )

    println("[bootRun] END   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
}
```

## GC 설정
Serial Garbage Collector
```shell
-XX:+UseSerialGC
```
* 싱글스레드로 동작한다.
* GC 가 수행되는 동안 애플리케이션을 멈춘다. 서버환경에 적합하지 않음

Parallel Garbage Collector
```shell
-XX:+UseParallelGC
-XX:ParallelGCThreads={N}
-XX:MaxGCPauseMillis={N}
-XX:GCTimeRatio={N}
```
* 멀티스레드로 동작한다.
* JVM 의 기본 GC 전략. GC 가 수행되는 동안 애플리케이을 멈춘다.
* `-XX:ParallelGCThreads={N}` 을 통해 GC 수행하는 스레드의 개수를 정의할 수 있다.
* `-XX:MaxGCPauseMillis={N}` 을 통해 GC 수행 시, 최대로 멈추는 시간을 정의할 수 있다.
* `-XX:GCTimeRatio={N}` 을 통해 GC 수행 시, 최대 처리량을 정의할 수 있다.

CMS Garbage Collector
```shell
-XX:+UseParNewGC
```
* Concurrent Mark & Sweep 을 수행한다.
  * [Mark & Sweep 개념](https://imasoftwareengineer.tistory.com/103)


## 테스트 해본 것.
* heap memory 를 min/max 동일하게 15M 정도 만들고, 동시에 api 호출 (GC 는 다르게 설정)
  * GC 를 Serial, Parallel 서로 다르게 헀는데, 잘 모르겠음. 속도차이도 없는듯. 너무 작게 하면 앱 띄어질 때 OOM 이 발생한다.

## 참고자료
* https://www.baeldung.com/spring-boot-heap-size
* https://www.baeldung.com/spring-boot-command-line-arguments
* https://www.baeldung.com/jvm-garbage-collectors
* https://www.baeldung.com/java-verbose-gc
* https://docs.oracle.com/en/java/javase/11/gctuning/introduction-garbage-collection-tuning.html#GUID-326EB4CF-8C8C-4267-8355-21AB04F0D304