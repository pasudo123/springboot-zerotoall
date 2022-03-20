package generic

import model.person.Bed
import model.person.Daughter
import model.person.GrandMother
import model.person.Mother

class GenericExample01

class GrandBed : Bed<GrandMother> {
    override fun sleep() {
        println("GrandMother sleep...")
    }
}

class MotherBed : Bed<Mother> {
    override fun sleep() {
        println("Mother sleep...")
    }
}

class DaughterBed : Bed<Daughter> {
    override fun sleep() {
        println("DaughterBed sleep...")
    }
}

class AnyBed : Bed<Any> {
    override fun sleep() {
        println("Any sleep...")
    }
}

var currentBed: Bed<out GrandMother>? = null

fun printOutSleepMessage(bed: Bed<out GrandMother>) {
    currentBed = bed
    currentBed!!.sleep()
    println(currentBed.toString())
}

var currentBedV2: Bed<in GrandMother>? = null

fun printInSleepMessage(bed: Bed<in GrandMother>) {
    currentBedV2 = bed
    currentBedV2!!.sleep()
    println(currentBedV2.toString())
}

fun main() {
    // 공변성 : 자신 + 하위객체 타입만 허용 : consume 개념 (인자를 리턴)
    // printOutSleepMessage(AnyBed()) // type mismatch
    printOutSleepMessage(GrandBed())
    printOutSleepMessage(MotherBed())
    printOutSleepMessage(DaughterBed())

    println("-")
    // 반공변성 : 자신 + 부모객체 타입만 허용 : product 개념 (인자를 사용)
    printInSleepMessage(AnyBed())
    printInSleepMessage(GrandBed())
    // printInSleepMessage(MotherBed()) // type mismatch
    // printInSleepMessage(DaughterBed())
}