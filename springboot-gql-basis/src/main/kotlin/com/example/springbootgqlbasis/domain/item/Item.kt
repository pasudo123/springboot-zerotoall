package com.example.springbootgqlbasis.domain.item

import com.example.springbootgqlbasis.domain.itemtag.ItemTag
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
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

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private val itemTags: MutableList<ItemTag> = mutableListOf()

    fun addItemTag(itemTag: ItemTag) {
        val itemTagIds = this.itemTags.map { it.id }
        if (itemTagIds.contains(itemTag.id)) {
            return
        }

        this.itemTags.add(itemTag)
        itemTag.set(this)
    }
}