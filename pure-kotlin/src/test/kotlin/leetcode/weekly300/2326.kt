package leetcode.weekly300

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class `2326` {

    @Test
    @DisplayName("테스트코드")
    fun leetcodeTest() {
        val listNode = ListNode(3)
        listNode.next = ListNode(0)
        listNode.next!!.next = ListNode(2)
        listNode.next!!.next!!.next = ListNode(6)
        listNode.next!!.next!!.next!!.next = ListNode(8)
        listNode.next!!.next!!.next!!.next!!.next = ListNode(1)
        listNode.next!!.next!!.next!!.next!!.next!!.next = ListNode(7)
        listNode.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(9)
        listNode.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(4)
        listNode.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(2)
        listNode.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(5)
        listNode.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(5)
        listNode.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next!!.next = ListNode(0)

        spiralMatrix(3, 5, listNode)
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun spiralMatrix(m: Int, n: Int, head: ListNode?): Array<IntArray> {

    var direction = 1
    var row = 0
    var col = 0
    var size = 0

    var current = head
    val matrix = Array(m) { IntArray(n) { -1 } }
    val visited = Array(m) { Array(n) { false } }

    while(current != null) {

        // to right
        if (direction == 1) {
            matrix[row][col++] = current.`val`
        }

        if (col >= n - size) {
            direction += 1
            col -= 1
            row += 1
            current = current.next
            continue
        }

        // to down
        if (direction == 2) {
            matrix[row++][col] = current.`val`
        }

        if (row >= m - size) {
            direction += 1
            row -= 1
            col -= 1
            current = current.next
            continue
        }

        // to left
        if (direction == 3) {
            matrix[row][col--] = current.`val`
        }

        if (col < 0 + size) {
            direction += 1
            col += 1
            row -= 1
            current = current.next
            continue
        }

        // to up
        if (direction == 4) {
            matrix[row--][col] = current.`val`
        }

        if (direction == 4 && row < 1 + size) {
            direction = 1
            row += 1
            col += 1
            current = current.next
            size++
            continue
        }

        current = current.next
    }

    return matrix
}