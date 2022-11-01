package com.example.springbootconcurrencyv2basis.withredis

import org.springframework.data.jpa.repository.JpaRepository

interface BagRepository : JpaRepository<Bag, Long>
