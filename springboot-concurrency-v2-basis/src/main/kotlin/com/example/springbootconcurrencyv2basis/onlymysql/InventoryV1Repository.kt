package com.example.springbootconcurrencyv2basis.onlymysql

import org.springframework.data.jpa.repository.JpaRepository

interface InventoryV1Repository : JpaRepository<InventoryV1, Long> {
}