package effectivekotlin

import kotlin.properties.Delegates

class item21

class LazyDemo {
    val name: String by lazy {
        println("init name")
        "hong"
    }

    val age: Int by lazy {
        println("init age")
        1
    }
}

class Person21 {
    var age: Int by Delegates.observable(1) { property, oldValue, newValue ->
        println("old=$oldValue, new=$newValue")
        this.doSomething()
    }

    fun doSomething() {
        println("current-age=$age")
    }
}

fun main() {
//    LazyDemo().also {
//        println("(1-name) ${it.name}")
//        println("(2-name) ${it.name}")
//    }
//
//    LazyDemo().also {
//        println("(1-age) ${it.age}")
//        println("(2-age) ${it.age}")
//    }

    Person21().apply {
        this.age = 5
        this.age = 6
        this.age = 7
    }
}