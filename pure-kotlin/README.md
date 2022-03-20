# pure-kotlin
* 코틀린 관련 공부를 하기 위함
* [코루틴 관련](./coroutine-readme.md)

# 질문과 답변들
### `reified (리이파이드) 어떻게 쓰는건지, 그리고 이거 역할이 뭔지.`
* reified 는 제네릭과 다르다. 
  * 제네릭은 컴파일 단계에서 버그를 일찍 감지할 수 있다. 근데 런타임에서는 문제가 될 수 있다.
  * 제네릭은 런타임에서 실제 원인과 멀리 떨어진 프로그램 한 지점에서 문제가 될 수 있다.
  * reified 를 쓰게 되면, 제네릭과 같이 타입을 전달하는 부분을 없앨 수 있다. -> 런타임에서도 해당 타입을 알 수 있기 때문이다.
  * reified 는 결과적으로 컴파일 때의 안정성과 런타임 때 유연함을 동시에 가지고 있는 것은 아닌가 싶다.
* reified 는 우선 inline keyword 와 같이 써야 한다.
  * inline 은 함수 호출부에 모든 코드를 다 넣는다. 함수의 내용을 호출되는 위치에 복사하게 된다.
  * inline 을 이용하면 대신 컴파일 되는 바이트 코드양이 많아진다. -> 함수를 별도로 호출하거나 추가적인 객체 생성은 없다. (메모리 오버헤드는 줄어듬)
  * inline 키워드는 1~3 줄 되는 길이의 함수에 사용하는 것이 효과적일 수 있다.
* reference
  * [generic](https://docs.oracle.com/javase/tutorial/java/generics/index.html)
  * [reified](https://codechacha.com/ko/kotlin-reified-keyword/)
  * [inline](https://codechacha.com/ko/kotlin-inline-functions/)
* [ReifiedExample01](./src/main/kotlin/reified/ReifiedExample01.kt)
* [ReifiedExample02](./src/main/kotlin/reified/ReifiedExample02.kt)

---
### `backing field & backing property`
* backing field
  * 직역하면 뒷받침하는 필드
  * kotlin class 의 property 를 조회/수정할 시에 이용한다. 
  * `field` 키워드를 이용한다.
* backing property
  * `field` 키워드를 노출하지 않으려면 backing properties 를 이용할 수 있다.
  * [Example01](./src/main/kotlin/encapsulation/Example01.kt)

---
### `공변성 vs 반공변성`
* 무공변성 (invariant)
  * 자신의 타입만 이용
* 공변성 (covariant)
  * 자기자신 + 자식객체를 허용 (하위자식 포함)
  * Java : <? extends T>
  * Kotlin : <out T>
  * produce : 자기자신 또는 자식객체를 리턴 : read only
* 반공변성 (contravariant)
  * 자기자신 + 부모객체를 허용 (상위부모 포함)
  * Java : <? super T>
  * Kotlin : <in T>
  * consume : 자기자신 또는 부모객체를 이용함 : write only 
* kotlin 에서 `in` & `out` 의 표기는 중심객체(가장 최상위 객체를 기준)
  * 바깥쪽방향의 타입을 허용할지 (나와 자식들)
  * 안쪽방향의 타입을 허용할지 (나와 부모들)
함수 인자 타입 : In Position, Out Position
```kotlin
interface Transformer<T> {
    // 인자 T : 인 포지션
    // 반환타입 T : 아웃 포지션
    fun transform(t: T): T
}
```
* [GenericExample01](./src/main/kotlin/generic/GenericExample01.kt)
* [참고 : 블로그](https://deep-dive-dev.tistory.com/39)
* [참고 : C#](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/concepts/covariance-contravariance/)

