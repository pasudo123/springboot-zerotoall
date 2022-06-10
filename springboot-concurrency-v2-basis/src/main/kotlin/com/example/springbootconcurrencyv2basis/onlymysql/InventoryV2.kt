package com.example.springbootconcurrencyv2basis.onlymysql

import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OptimisticLockType
import org.hibernate.annotations.OptimisticLocking
import org.hibernate.annotations.SelectBeforeUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "inventory_v2")
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@SelectBeforeUpdate
class InventoryV2 {

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

    companion object {
        private const val MAX_SIZE = 10L
    }
}