## undertow 
### io thread
* 클라이언트 요청을 비동기로 수행한다.
* cpu core 당 2개의 io thread 가 적절 기본값이다.

### worker thread
* io thread 가 요청받은 건을 스레드 풀에서 꺼내 블럭킹되는 작업들을 수행한다.
  * ex) worker thread 가 1개라고 가정했을 때, 2초가 걸리는 작업이 동시에 두 번 들어온다면 2초간 작업을 수행하고 나머지 작업을 2초 수행한다.
* 해당 값은 일반적으로 크게 설정한다. cpu core 당 10개로 세팅한다.

### Why isn’t Undertow based on Mina, Netty, RNIO, Grizzly, or <insert network framework>?
```shell
In order to best achieve its goals, 
Undertow requires very close integration with the underlying I/O infrastructure in the Java platform. 

The simplicity offered by any abstraction comes from hiding the underlying mechanisms behind it. 
However, the dilemma is that building an extremely efficient and flexible web server requires customization and control of these mechanisms. 
Undertow attempts to strike the right balance by reusing a minimalistic I/O library, XNIO, that was created for WildFly’s remote invocation layer.

XNIO allows us to eliminate some boiler plate, 
and also allows for direct I/O integration with the operating system, 
but it does not go further than that. 

In addition, 
XNIO offers very strong backwards compatibility which is important since this is also a concern for the Undertow project. 
Of course, other projects may have different needs, and thus might make different choices.
```

## reference
* https://undertow.io/undertow-docs
* [undertow-io example](https://github.com/undertow-io/undertow/blob/master/examples/README)