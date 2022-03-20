package reified

class ReifiedExample02

fun <T> printTypeWithGeneric(type: T) {
    // 구체화된 유형 매개변수로 T 를 사용할 수 없다.
    // Cannot use 'T' as (reified type parameter)
    // 컴파일 단계에서 런타임에 실행하는 메소드에 대해서 실행을 할 수 없음을 알린다. -> 런타임에서 타입을 알 수 없다.
    /**
    when (T::class.java) {

    }
    **/
}

fun <T> T.printTypeWithGenericAdvanced(type: Class<T>) {
    // 해당 타입이 건네서 들어와야 한다.
    when (type) {
        String::class.java -> { println("String") }
        Int::class.java -> { println("Int") }
        Long::class.java -> { println("Long") }
        else -> { println("else : $type") }
    }
}

inline fun <reified T : Any> T.printTypeWithReified() {
    // 런타임에서도 어느 타입인지 명시적으로 전달하고 있다. 따라서 별도의 타입을 전달할 필요가 없다.
    when (T::class.java) {
        String::class.java -> { println("String 입니다.") }
        Int::class.java -> { println("Int 입니다.") }
        Long::class.java -> { println("Long 입니다.") }
        java.lang.Long::class.java -> { println("ava.lang.Long 입니다.") }
        java.lang.Integer::class.java -> { println("Integer 입니다.") }
        else -> { println("else : ${T::class.java}") }
    }
}

fun main() {

    val intDummies = Dummies<Int>()
    intDummies.addItem(1)
    intDummies.addItem(2)

    val stringDummies = Dummies<String>()
    stringDummies.addItem("1")
    stringDummies.addItem("2")
}

class Dummies<T> private constructor (
    private val items: MutableList<T> = mutableListOf()
) {

    constructor(): this(mutableListOf<T>())

    fun addItem(item: T) {
        items.add(item)
    }
}