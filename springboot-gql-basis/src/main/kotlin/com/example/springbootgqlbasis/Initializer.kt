package com.example.springbootgqlbasis

import com.example.springbootgqlbasis.domain.item.Item
import com.example.springbootgqlbasis.domain.item.ItemRepository
import com.example.springbootgqlbasis.domain.itemtag.ItemTag
import com.example.springbootgqlbasis.domain.itemtag.ItemTagRepository
import com.example.springbootgqlbasis.domain.notice.Notice
import com.example.springbootgqlbasis.domain.notice.NoticeRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Component(value = "Initializer")
@Transactional
class Initializer(
    private val itemRepository: ItemRepository,
    private val itemTagRepository: ItemTagRepository,
    private val noticeRepository: NoticeRepository
) {

    fun save() {
        this.saveFoodItems()
        this.saveCarItems()
        this.saveNotices()
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
            foodItemTags[index].forEach { itemTag ->
                itemTagRepository.save(itemTag)
                itemTag.set(item)
            }
        }
    }

    private fun saveNotices() {

        val notices = listOf(
            Notice("오늘 아침 공지", "안녕하세요. 반갑습니다."),
            Notice("스프링부트 스터디 공지", "오늘은 스프링부트를 공부할 것입니다."),
            Notice("오늘 하루 공지", "즐거운 하루였습니다."),
            Notice("기분 좋은 하루 공지", "좋은 하루 보내세요."),
            Notice("커피숍 공지", "오늘 아메리카노 10퍼센트 할인"),
            Notice("옷가게 공지", "오늘 F/W 상품 20퍼센트 할인 및 양말 1+1"),
        )

        notices.forEach { notice ->
            repeat(Random.nextInt(20)) {
                notice.plusVotes()
            }

            noticeRepository.save(notice)
        }
    }
}