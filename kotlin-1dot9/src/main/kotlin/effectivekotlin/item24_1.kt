package effectivekotlin

class item24_1

open class Animal(val name: String)

class Dog(name: String) : Animal(name)
class Cat(name: String) : Animal(name)

interface InverseComparator<T> : Comparator<T> {
    override fun compare(a: T, b: T): Int
}

fun main() {
    val togetherComparator = object : InverseComparator<Animal> {
        override fun compare(a: Animal, b: Animal): Int {
            return a.name.compareTo(b.name)
        }
    }

    val dogs = listOf(Dog("Buddy"), Dog("Max"), Dog("Apple"))
    val cats = listOf(Cat("Whiskers"), Cat("Mittens"), Cat("Coco"))
    val together = dogs + cats

    val sortedDogs = dogs.sortedWith(togetherComparator)
    val sortedCats = cats.sortedWith(togetherComparator)
    val sortedTogethers = together.sortedWith(togetherComparator)

    println("Sorted dogs: ${sortedDogs.map { it.name }}")
    println("Sorted cats: ${sortedCats.map { it.name }}")
    println("Sorted togethers: ${sortedTogethers.map { it.name }}")
}