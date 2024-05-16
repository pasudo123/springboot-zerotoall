package com.example.springbootjpabasis.study01

import com.example.springbootjpabasis.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "food",
    indexes = [
        Index(name = "food_name_unique_idx", unique = true, columnList = "name")
    ]
)
class Food(
    @Column(name = "name", length = 32, nullable = false)
    val name: String,
    @Column(name = "sub_name", length = 32, nullable = true)
    val subName: String? = null
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    var state: State = State.INIT

    enum class State {
        INIT, GOOD, BAD
    }

    fun toGood() {
        this.state = State.GOOD
    }

    fun toBad() {
        this.state = State.BAD
    }
}
