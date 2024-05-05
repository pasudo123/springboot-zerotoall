package effectivekotlin

class item45

fun main() {
    val hello1 = "hello"
    val hello2 = "hello"

    println("hello1 == hello2 >> ${hello1 == hello2}")
    println("hello1 === hello2 >> ${hello1 === hello2}")

    var hello3 = String(charArrayOf('h', 'e', 'l', 'l', 'o'))
    println("hello3 >> ${hello3}")
    println("hello1 == hello3 >> ${hello1 == hello3}")

    // intern 메소드 수행, 스트링 객체를 string constant pool 에 보관
    println("intern 메소드 수행 전, hello1 === hello3 >> ${hello1 === hello3}")
    hello3 = hello3.intern()
    println("intern 메소드 수행 후, hello1 === hello3 >> ${hello1 === hello3}")
}