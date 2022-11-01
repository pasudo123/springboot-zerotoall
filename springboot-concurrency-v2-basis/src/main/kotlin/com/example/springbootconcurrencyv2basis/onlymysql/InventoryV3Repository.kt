package com.example.springbootconcurrencyv2basis.onlymysql

import org.springframework.data.jpa.repository.JpaRepository

interface InventoryV3Repository : JpaRepository<InventoryV3, Long>
