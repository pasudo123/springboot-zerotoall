package com.example.springbootconcurrencyv2basis.onlymysql

import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("inventory/v1")
class InventoryController(
    private val inventoryV1Repository: InventoryV1Repository
) {

    @PostMapping
    fun createInventory(): InventoryV1 {
        return inventoryV1Repository.save(InventoryV1())
    }

    @Transactional
    @PostMapping("{id}")
    fun addItemByInventoryId(@PathVariable id: Long): InventoryV1 {
        inventoryV1Repository.findByIdOrNull(id)?.let {
            it.addItemIfPossibleOrThrow()
            return it
        }

        throw RuntimeException("인벤토리 미확인 : $id")
    }

    @GetMapping("{id}")
    fun getInventoryById(@PathVariable id: Long): InventoryV1 {
        return inventoryV1Repository.findByIdOrNull(id) ?: throw RuntimeException("인벤토리 미확인 : $id")
    }
}