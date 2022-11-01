package com.example.springbootconcurrencyv2basis.withredis

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bag")
class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    var size: Long = 0L
        protected set

    fun addItemIfPossibleOrThrow() {
        if (size >= MAX_SIZE) {
            throw RuntimeException("인벤토리에 아이템이 가득 찼습니다.")
        }

        size += 1
    }

    fun isOverflowBagSize(incrSize: Long): Boolean {
        return incrSize > MAX_SIZE
    }

    companion object {
        private const val MAX_SIZE = 10L
    }
}
