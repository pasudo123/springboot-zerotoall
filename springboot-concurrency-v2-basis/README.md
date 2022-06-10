## springboot-concurrency-v2-basis
* JPA + MySQL 기준으로 락 살펴보기

### 기본
* 동시에 update 쿼리 적용안됨, 에러 발생안함

---
### OptimisticLock : 낙관적 락
* 디비 로우에 락을 유지하지 않는다. -> `업데이트/삭제될 때 다른 트랜잭션이 건드리지 않을것이라고 판단` -> 낙관적으로 본다.
* 타 트랜잭션들에 의해 동시에 값의 변경이 일어나는 경우 에러가 발생한다.
* > 동시읽기 > 동시쓰기 인 경우에 유리하다고 한다. : 낙관적 잠금은 업데이트 손실이 많기 때문이라고 한다. (동시에 요청올때마다 매번 실패할테니..)

### OptimisticLock with JPA 
* 동시에 update 쿼리 적용안됨, 에러 발생
  * `org.hibernate.StaleObjectStateException : Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect)` 
* @Version 을 붙임으로써 JPA 단에서 OptimisticLock 이 적용된다.
* @Version 은 없고, @OptimisticLocking(type = OptimisticLockType.NONE) 만 붙이면 별도 에러가 나지 않는다.
* @Version 은 없고, @OptimisticLocking(type = OptimisticLockType.VERSION) 만 붙이면 별도 에러가 나지 않는다.
  * @Version 이랑 같이 적용되어야 한다.
* @OptimisticLocking(type = OptimisticLockType.DIRTY), @OptimisticLocking(type = OptimisticLockType.ALL)
  * DIRTY 의 경우 변경하는 특정 컬럼의 값을 통해 업데이트 여부를 확인한다.
  * ALL 의 경우 특정 테이블의 모든 컬럼의 값을 통해 업데이트 여부를 확인한다.
  * @DynamicUpdate 애노테이션을 붙여준다.
  * @DynamicUpdate 가 안붙어있다면, `optimistic-lock=all|dirty requires dynamic-update="true"` 에러를 만난다.
* @OptimisticLocking(type = OptimisticLockType.DIRTY) 이용 시, @SelectBeforeUpdate 를 같이 사용한다.
  * @SelectBeforeUpdate 는 update query 를 수행한 디비 세션객체가 commit 하기 이전에 타 세션에서 해당 데이터 로우를 수정하지 못하도록 한다.

---
### PessimisticLock : 비관적 락
* 데이터베이스 Lock 이용
* Lock 을 획득한 클라이언트가 Lock 을 획득하지 못하면, DB Lock이 발생할 수 있다.
* Lock 수행 시에, 타임아웃 설정을 두어 타임아웃 시 자동으로 Lock 이 해제될 수 있도록 한다.
* > 동시읽기 < 동시쓰기 인 경우에 유리하다고 한다.

### PessimisticLock with JPA
* LockModeType.PESSIMISTIC_READ
  * Query 로 SELECT ... FOR SHARE 가 날라간다.
    * 읽는건 제한이 없지만, 커밋되기 이전까지 다른 트랜잭션이 커밋하지 못한다. 현재 수행중인 세션의 커밋이 완료될때까지 기다려야 한다.
  * 동시에 업데이트 수행 시 에러가 발생함
    * `MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction` 에러가 발생한다.
    * [Deadlock found when trying to get lock : mysql doc](https://dev.mysql.com/doc/refman/8.0/en/innodb-deadlock-example.html)
    * SHARE MODE 로 table row 락 획득, 타 트랜잭션에서 처리하려고 하는 경우라서 발생한다.
* LockModeType.PESSIMISTIC_WRITE
  * Query 로 SELECT ... FOR UPDATE 가 날라간다. (=@SelectBeforeUpdate)
  * 현재 수행중인 세션이 작업을 완료하기 이전까지 데이터 로우를 수정하지 못한다. 
  * 동시에 업데이트 수행 시 에러는 발생하지 않는다. 그리고 동시요청에 대해서도 업데이트는 잘된다.
  * row exclusive lock

---
### JPA Lock Error & Retry
* https://stackoverflow.com/questions/12252535/recovering-from-hibernate-optimistic-locking-exception
* https://stackoverflow.com/questions/21672428/retry-mechanism-for-optimistic-locking-spring-data-jpa

---
### 참고자료
* https://blog.zestmoney.in/handling-concurrent-updates-with-optimistic-and-pessimistic-locking-in-jpa-c26d0b6855e7
* https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#locking
* [JPA Lock 이해하기](https://reiphiel.tistory.com/entry/understanding-jpa-lock)
* [@SelectBeforeUpdate](https://dololak.tistory.com/446)
* https://dev.mysql.com/doc/refman/8.0/en/innodb-locking-reads.html