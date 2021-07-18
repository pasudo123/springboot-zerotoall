package com.example.springbootconcurrencybasis.lockdomain.shoppingbag.model

import com.example.springbootconcurrencybasis.domain.BaseEntity
import com.example.springbootconcurrencybasis.lockdomain.shoppingbag.api.dto.ShoppingBagDto
import com.example.springbootconcurrencybasis.lockdomain.snack.model.Snack
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "shopping_bag")
class ShoppingBag private constructor(
    val name: String,
    val holdCount: Long
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Snack::class, mappedBy = "shoppingBag")
    var snacks: MutableList<Snack> = mutableListOf()

    fun addSnack(snack: Snack) {
        snacks.removeIf {savedSnack -> savedSnack.id == snack.id }
        snacks.add(snack)
        snack.into(this)
    }

    companion object {
        fun from(createRequest: ShoppingBagDto.CreateRequest): ShoppingBag {
            return createRequest.run {
                ShoppingBag(
                    this.name,
                    this.holdCount
                )
            }
        }
    }
}