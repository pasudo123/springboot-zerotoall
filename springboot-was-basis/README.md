## undertow 
### io thread
* 클라이언트 요청을 비동기로 수행한다.
* cpu core 당 2개의 io thread 가 적절 기본값이다.

### worker thread
* io thread 가 요청받은 건을 실제로 애플리케이션 레벨에서 수행한다.
* 블럭킹된 작업들을 스레드 풀에서 꺼내서 쓰고 반납하는 형태로 동작한다.
* 해당 값은 일반적으로 크게 설정한다. cpu core 당 10개로 세팅한다.

## reference
* https://undertow.io/undertow-docs