package com.example.springbootconcurrencybasis.lockdomain.shoppingbag.repository

import com.example.springbootconcurrencybasis.lockdomain.shoppingbag.model.ShoppingBag
import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingBagRepository : JpaRepository<ShoppingBag, Long>