## springboot-gql-basis
* springboot 와 graphql 같이 사용하기
* graphql plugins 도 인텔리제이에 설치해주자
    * `js graphql`

## graphql
1. 디렉토리 만들고 그 안에서 `npm install apollo-server graphql` 수행
2. playground plugins 설치한다. https://www.apollographql.com/docs/apollo-server/testing/build-run-queries/#graphql-playground
  * playground 는 브라우저 환경에서 gql 에 대해 쿼리, 뮤테이션을 테스트할 수 있도록 해줄 수 있도록 도와준다.
3. datasource 이용 ()
  * apollo server 가 rest api 나 db 에서 데이터를 들고오기 위해 사용하는 클래스
  * RestDataSource 는 일반적으로 api 와 통신할때 사용한다.
    * willSendRequest 라는 메소드를 오버라이딩 하면 인터셉터의 효과를 얻을 수 있다.

## 읽을 목차
* https://www.apollographql.com/docs/apollo-server/schema/schema/
* https://www.apollographql.com/docs/apollo-server/data/data-sources/#restdatasource-reference
    