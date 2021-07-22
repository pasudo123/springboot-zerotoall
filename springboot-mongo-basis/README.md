# springboot-mongo-basis

## 여기서 다루는 것
* mongodb 를 이용한 CRUD rest api 만들기

## run
```shell
$ ./docker-compose up -docker
```
* localhost:8081 접속 (mongo-express 접속)

## 몽고디비 특징
* 몽고디비는 rdb의 테이블에 매핑되는 개념인 Document 가 있다.
  * Document 에는 데이터 내용을 json 형식으로 저장한다.
* Index 를 지원한다. : 대용량 데이터를 다양한 조건으로 조회할 수 이다.
  * 카카오에서 자주 쓰는 인덱스 : Hashed Index, Geospatial Index, TTL Index 가 있다.
* 공간인덱스 + 일반인덱스 = 복합인덱스 이용
    

## reference
* https://youtu.be/ssj0CGxv60k
* [mongo spring-data doc](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#reference)
* [mongo doc : java driver](https://docs.mongodb.com/drivers/java/sync/current/)
* [mongo db : if kakao](https://if.kakao.com/session/126)