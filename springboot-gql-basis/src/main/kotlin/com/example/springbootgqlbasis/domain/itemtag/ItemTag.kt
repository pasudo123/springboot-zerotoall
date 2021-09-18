package com.example.springbootgqlbasis.domain.itemtag

import com.example.springbootgqlbasis.domain.item.Item
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "item_tag")
class ItemTag (
    @Column(name = "name")
    val name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var item: Item? = null

    fun set(item: Item) {
        this.item = item
        this.item!!.addItemTag(this)
    }
}