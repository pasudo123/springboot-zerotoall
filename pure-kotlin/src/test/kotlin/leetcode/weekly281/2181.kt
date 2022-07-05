package leetcode.weekly281

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class `2181` {

    @Test
    fun leetcodeTest() {
        val head1 = ListNode(0)
        head1.next = ListNode(3)
        head1.next!!.next = ListNode(1)
        head1.next!!.next!!.next = ListNode(0)
        head1.next!!.next!!.next!!.next = ListNode(0)
        head1.next!!.next!!.next!!.next!!.next = ListNode(4)
        head1.next!!.next!!.next!!.next!!.next!!.next = ListNode(5)
        head1.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(2)
        head1.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(0)

        mergeNodes(head1)
    }
}

fun mergeNodes(head: ListNode?): ListNode? {

    val sumGroup = mutableListOf<Int>()
    var sum = 0
    var current = head!!.next
    while(current != null) {

        if (current.`val` == 0) {
            sumGroup.add(sum)
            sum = 0
        } else {
            sum += current.`val`
        }

        current = current.next
    }

    val resultHead = ListNode(0)
    var loopHead = resultHead

    sumGroup.forEach { element ->
        loopHead.next = ListNode(element)
        loopHead = loopHead.next!!
    }

    return resultHead.next
}

