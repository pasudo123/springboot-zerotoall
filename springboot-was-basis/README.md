## undertow 
### io thread
* 클라이언트 요청을 비동기로 수행한다.
* cpu core 당 2개의 io thread 가 적절 기본값이다.

### worker thread
* io thread 가 요청받은 건을 스레드 풀에서 꺼내 블럭킹되는 작업들을 수행한다.
  * ex) worker thread 가 1개라고 가정했을 때, 2초가 걸리는 작업이 동시에 두 번 들어온다면 2초간 작업을 수행하고 나머지 작업을 2초 수행한다.
* 해당 값은 일반적으로 크게 설정한다. cpu core 당 10개로 세팅한다.

## reference
* https://undertow.io/undertow-docs