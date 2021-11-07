package com.example.springboottestcodebasis.domain.member.model

import com.example.springboottestcodebasis.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "entity_member")
@Table(name = "member")
class Member(
    paramName: String,
    paramAge: Int
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "name")
    var name: String? = paramName
        protected set

    @Column(name = "age")
    var age: Int? = paramAge
        protected set
}