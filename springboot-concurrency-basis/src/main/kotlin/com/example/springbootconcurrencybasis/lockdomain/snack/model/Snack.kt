package com.example.springbootconcurrencybasis.lockdomain.snack.model

import com.example.springbootconcurrencybasis.domain.BaseEntity
import com.example.springbootconcurrencybasis.lockdomain.shoppingbag.model.ShoppingBag
import com.example.springbootconcurrencybasis.lockdomain.snack.api.dto.SnackDto
import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(name = "snack")
class Snack : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "name", columnDefinition = "VARCHAR(50)", nullable = false)
    var name: String? = null
        protected set

    @Column(name = "price", columnDefinition = "BIGINT", nullable = false)
    var price: Long? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ShoppingBag::class)
    @JoinColumn(name = "shopping_bag_id")
    var shoppingBag: ShoppingBag? = null
        protected set

    @Version
    @Column(name = "opt_lock")
    var version: Long? = null
        protected set

    fun into(shoppingBag: ShoppingBag) {
        this.shoppingBag = shoppingBag
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun updatePrice(price: Long) {
        this.price = price
    }

    companion object {
        fun from(createRequest: SnackDto.CreateRequest): Snack {
            return Snack().apply {
                name = createRequest.name
                price = createRequest.price
            }
        }
    }
}