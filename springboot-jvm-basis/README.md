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
        "-verbose:gc"
    )

    println("[bootRun] END   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
}
```

## GC 설정
__Serial Garbage Collector__
```shell
-XX:+UseSerialGC
```
* 싱글스레드로 동작한다.
* GC 가 수행되는 동안 애플리케이션을 멈춘다. 서버환경에 적합하지 않음

__Parallel Garbage Collector__
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

__CMS Garbage Collector__
```shell
-XX:+UseParNewGC
```
* Concurrent Mark & Sweep 을 수행한다.
  * [Mark & Sweep 개념](https://imasoftwareengineer.tistory.com/103)
* `java SE 8` 에서 deprecated 될 예정이었고, 더이상 쓰이지 않게 될 수 있다.
* `java 9` 에서는 deprecated 되었다.
* `java 14` 에서는 완전히 사라졌다.

__G1 Garbage Collector__
```shell
-XX:+UseG1GC
```
* 다른 GC 와는 다르게 힙공간을 일정한 크기의 힙 region 으로 파티셔닝한다.
* CMS Garbage Collector 의 대체로 쓰인다.
* JDK11 부터 default GC 이다.

## Stop The World (STW)
* GC 를 수행하기 위해 GC 수행을 위한 스레드만 작동하고 나머지 애플리케이션에서 동작중인 스레드는 모두 작업을 멈춘다.
* 어떤 GC 알고리즘을 사용하더라도 STW 는 발생한다.
* 대개 GC 튜닝이란 STW 의 소요시간을 줄이는 것이다.
* https://stophyun.tistory.com/37

## GC 튜닝이 필요한 경우?
* STW 가 잦게 발생해서, 애플리케이션 단에서 타임아웃이 잦은 경우
* Xms, Xmx 옵션을 설정하지 않았다.

## GC Logging
```shell
-verbose:gc
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps # java9 부터는 제거
-XX:+PrintGCTimeStamps # java9 부터는 제거
-Xlog:gc*::time # java9 이후부터 이용
```
* application gc 로그를 출력할 수 있다.
* jvm args 옵션에 `-verbose:gc` 표기
* 추가적인 세부정보를 더 보기위해선, `-XX:+PrintGCDetails` 를 같이 이용한다.
* 날짜와 시간정보 추가 `-XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps` java9 부터는 제거되었다.
  * `-Xlog:gc*::time` 로 대체

## Xms & Xmx 를 동일하게 세팅하는 이유
* jvm 기반 애플리케이션이 띄어질 때, 초기 Xms 사이즈만큼의 힙메모리 사이즈를 취하고 더 필요한 경우에는 os 로부터 메모리를 추가요청한다.
  * os 로 추가요청하는 시점에서도 어느정도 딜레이가 발생한다.
  * 결국 jvm 은 Xmx 만큼 힙메모리 사이즈를 취하게 될 것이므로 동일하게 가져가는 것이 좋다.
* GC 의 발생빈도가 잦아지는 부분을 해소 (Young GC 부분)
  * Xms 값을 낮게하면 GC 가 자주 발생한다. 대신 GC 수행 속도는 짧다. (GC 는 여러번, 수행속도는 짧게)
    * 초기에 할당받은 힙메모리가 작기 때문에 young generation 에서 자주 발생 (eden, s0, s1)
  * Xms == Xmx 로 하면 GC 가 적게 발생한다. 대신 GC 수행 속도는 상대적으로 길다.
  * ex) [32.719s][info][gc] GC(147) Pause Young (Normal) (G1 Evacuation Pause) 17M->16M(22M) 0.712ms

## 참고자료
* https://www.baeldung.com/spring-boot-heap-size
* https://www.baeldung.com/spring-boot-command-line-arguments
* https://www.baeldung.com/jvm-garbage-collectors
* https://www.baeldung.com/java-verbose-gc
* https://www.baeldung.com/jvm-parameters
* https://docs.oracle.com/en/java/javase/11/gctuning/introduction-garbage-collection-tuning.html
* https://docs.oracle.com/en/java/javase/18/gctuning/introduction-garbage-collection-tuning.html
* [Garbage Collection 튜닝](https://d2.naver.com/helloworld/37111)
* [우아한 형제들 메모리릭 글](https://techblog.woowahan.com/2628/)
