package com.example.springbootgqlbasis.domain.item

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "item")
class Item (
    @Column(name = "name")
    val name: String,
    @Column(name = "price")
    val price: Double
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set
}