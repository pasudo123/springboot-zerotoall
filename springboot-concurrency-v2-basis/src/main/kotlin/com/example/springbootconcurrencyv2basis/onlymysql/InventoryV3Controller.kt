package com.example.springbootconcurrencyv2basis.onlymysql

import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityManager
import javax.persistence.LockModeType

@RestController
@RequestMapping("inventory/v3")
class InventoryV3Controller(
    private val inventoryV3Repository: InventoryV3Repository,
    private val entityManager: EntityManager
) {

    @PostMapping
    fun createInventory(): InventoryV3 {
        return inventoryV3Repository.save(InventoryV3())
    }

    @Transactional
    @PostMapping("{id}/ps-read")
    fun addItemByInventoryIdWithPsRead(@PathVariable id: Long): InventoryV3 {
        val inventoryV3 = entityManager.find(InventoryV3::class.java, id, LockModeType.PESSIMISTIC_READ)
            ?: throw RuntimeException("인벤토리 미확인 : $id")

        inventoryV3.addItemIfPossibleOrThrow()
        return inventoryV3
    }

    @Transactional
    @PostMapping("{id}/ps-write")
    fun addItemByInventoryIdWithPsWrite(@PathVariable id: Long): InventoryV3 {
        val inventoryV3 = entityManager.find(InventoryV3::class.java, id)
            ?: throw RuntimeException("인벤토리 미확인 : $id")
        entityManager.lock(inventoryV3, LockModeType.PESSIMISTIC_WRITE)

        inventoryV3.addItemIfPossibleOrThrow()
        return inventoryV3
    }

    @GetMapping("{id}")
    fun getInventoryById(@PathVariable id: Long): InventoryV3 {
        println()
        return inventoryV3Repository.findByIdOrNull(id) ?: throw RuntimeException("인벤토리 미확인 : $id")
    }
}