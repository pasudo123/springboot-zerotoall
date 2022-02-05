# springboot with quartz
* Job, JobDetail, Trigger, Scheduler 가 존재
* 스프링을 통해 관리하지만, 관리하는 방식은 두가지
    * 스프링 (편의 클래스 사용)
    * 쿼츠

# 개념
* JobDetail
    * 잡 디테일을 이용하여 실제 잡을 정의할 수 있다.
* Trigger
    * 트리거를 이용하여 잡 실행에 대한 메커니즘을 제공할 수 있다. (언제 잡이 실행될 것인지?)
    * 트리거 인스턴스는 Job execute 를 fire 한다.
* JobStore
    * 잡 스토어는 잡과 트리거의 저장소 메커니즘을 수행한다.
    * 작업 스케줄과 관련된 데이터를 유지하기 위한 책임을 가지고 있다.
    * 메모리 방식 또는 영속성으로 저장하는 방식이 존재한다.
* Schedule
    * 스케줄러는 잡 스케줄러를 구현하기 위한 메인 인터페이스이다.
    * 스케줄러 팩토리를 상속받고, 처음 생성할 시에 잡과 트리거를 등록한다.

# SpringBeanJobFactory vs QuartzJobBean
* Job 을 빈으로 등록하기 위해 2가지 방식이 제공되고 있다. 차이가 궁금하다.
    * 찾아보면 두 개는 유사하다.
* bean-style 로 `SpringBeanJobFactory` 를 이용하여 잡을 빈으로 등록할 수 있다. (이때는 constructor injection 에 제약이 있음)
* QuartzJobBean 은 Job interface 를 별도로 구현해서 한번더 래핑한 클래스다.
    * 근데 왜 설명글에는 종속성 주입을 선호하는 방법이, `Note that the preferred way to apply dependency injection to Job instances is via a JobFactory` 이라고 하는걸까
    * QuartzJobBean 은 잡을 빈으로 등록이 되긴하던데.. ?

[QuartzJobBean 설명글](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/quartz/QuartzJobBean.html)
```java
Simple implementation of the Quartz Job interface, 
applying the passed-in JobDataMap and also the SchedulerContext as bean property values. 
This is appropriate because a new Job instance will be created for each execution. 
JobDataMap entries will override SchedulerContext entries with the same keys.
```

[SpringBeanJobFactory 설명글](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/quartz/SpringBeanJobFactory.html)
```java
Subclass of AdaptableJobFactory that also supports Spring-style dependency injection on bean properties. 
This is essentially the direct equivalent of Spring's QuartzJobBean in the shape of a Quartz JobFactory.
Applies scheduler context, job data map and trigger data map entries as bean property values. 
If no matching bean property is found, the entry is by default simply ignored. 
This is analogous to QuartzJobBean's behavior.
```

# JobExecutionContext.mergedJobDataMap
* jobDetail 내의 JobDataMap 과 trigger 내의 JobDataMap 을 같이 들고올 수 있다.

아래 두 내용을 합친 것이다.
```kotlin
context.trigger.jobDataMap + context.jobDetail.jobDataMap
```

# reference
* https://www.baeldung.com/spring-quartz-schedule
* https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#io.quartz