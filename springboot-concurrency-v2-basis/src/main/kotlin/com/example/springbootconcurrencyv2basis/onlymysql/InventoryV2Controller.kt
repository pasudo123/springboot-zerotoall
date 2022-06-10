package com.example.springbootconcurrencyv2basis.onlymysql

import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("inventory/v2")
class InventoryV2Controller(
    private val inventoryV2Repository: InventoryV2Repository
) {

    @PostMapping
    fun createInventory(): InventoryV2 {
        return inventoryV2Repository.save(InventoryV2())
    }

    @Transactional
    @PostMapping("{id}")
    fun addItemByInventoryId(@PathVariable id: Long): InventoryV2 {
        inventoryV2Repository.findByIdOrNull(id)?.let {
            it.addItemIfPossibleOrThrow()
            return it
        }

        throw RuntimeException("인벤토리 미확인 : $id")
    }

    @GetMapping("{id}")
    fun getInventoryById(@PathVariable id: Long): InventoryV2 {
        println()
        return inventoryV2Repository.findByIdOrNull(id) ?: throw RuntimeException("인벤토리 미확인 : $id")
    }
}