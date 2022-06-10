package com.example.springbootconcurrencyv2basis.onlymysql

import org.springframework.data.jpa.repository.JpaRepository

interface InventoryV2Repository : JpaRepository<InventoryV2, Long>