package com.example.springbootgqlbasis

import com.example.springbootgqlbasis.domain.item.Item
import com.example.springbootgqlbasis.domain.item.ItemRepository
import com.example.springbootgqlbasis.domain.itemtag.ItemTag
import com.example.springbootgqlbasis.domain.itemtag.ItemTagRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component(value = "Initializer")
@Transactional
class Initializer(
    private val itemRepository: ItemRepository,
    private val itemTagRepository: ItemTagRepository
) {

    fun save() {
        this.saveCarItems()
        this.saveFoodItems()
    }

    private fun saveCarItems() {

        val carItems = listOf(
            Item("아우디 A6", 50000.0, Item.Type.CAR),
            Item("벤츠 C-Class", 100000.0, Item.Type.CAR),
            Item("지프 Gladiator", 7070000.0, Item.Type.CAR),
            Item("볼브 XC90", 800000.0, Item.Type.CAR),
        )

        carItems.forEach { item ->
            itemRepository.save(item)
        }
    }

    private fun saveFoodItems() {

        val foodItems = listOf(
            Item("후라이드 통닭", 5000.0, Item.Type.FOOD),
            Item("양념통닭", 300.0, Item.Type.FOOD),
            Item("간장통닭", 2500.0, Item.Type.FOOD),
            Item("마늘통닭", 400.0, Item.Type.FOOD),
            Item("페페로니 피자", 250.0, Item.Type.FOOD),
            Item("포테이토 피자", 10.0, Item.Type.FOOD),
        )

        foodItems.forEach { foodItem ->
            itemRepository.save(foodItem)
        }

        val foodItemTags = arrayListOf(
            listOf(ItemTag("후라이드"), ItemTag("닭"), ItemTag("치킨")),
            listOf(ItemTag("양념")),
            listOf(ItemTag("간장"), ItemTag("단짠단짠")),
            listOf(ItemTag("매콤함"), ItemTag("통마늘")),
            listOf(ItemTag("페페로니")),
            listOf(ItemTag("감자"), ItemTag("포테이토"))
        )

        foodItems.forEachIndexed { index, item ->
            val foundItem = itemRepository.findById(item.id!!).get()
            foodItemTags[index].forEach { itemTag ->
                itemTagRepository.save(itemTag)
                itemTag.set(foundItem)
            }
        }
    }
}