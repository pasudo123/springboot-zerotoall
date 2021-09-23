package com.example.springbootgqlbasis.domain.item

import com.example.springbootgqlbasis.domain.itemtag.ItemTag
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
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
    val price: Double,
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: Type
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    val itemTags: MutableList<ItemTag> = mutableListOf()

    enum class Type(desc: String) {
        FOOD("음식"),
        LIFE("생활"),
        FASHION("패션"),
        CAR("자동차")
    }

    fun addItemTag(itemTag: ItemTag) {
        val itemTagIds = this.itemTags.map { it.id }
        if (itemTagIds.contains(itemTag.id)) {
            return
        }

        this.itemTags.add(itemTag)
        itemTag.set(this)
    }
}