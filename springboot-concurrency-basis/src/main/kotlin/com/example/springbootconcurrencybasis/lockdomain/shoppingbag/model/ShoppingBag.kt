package com.example.springbootconcurrencybasis.lockdomain.shoppingbag.model

import com.example.springbootconcurrencybasis.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "shopping_bag")
class ShoppingBag private constructor(
    val holdCount: Long
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    
}