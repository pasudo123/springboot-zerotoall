package com.example.springbootgqlbasis

import com.example.springbootgqlbasis.domain.item.Item
import com.example.springbootgqlbasis.domain.item.ItemRepository
import com.example.springbootgqlbasis.domain.itemtag.ItemTag
import com.example.springbootgqlbasis.domain.itemtag.ItemTagRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringbootGqlBasisApplication {

    @Bean
    fun init(
        itemRepository: ItemRepository,
        itemTagRepository: ItemTagRepository
    ) = CommandLineRunner {

        val items = listOf(
            Item("후라이드 통닭", 5000.0),
            Item("양념통닭", 300.0),
            Item("간장통닭", 2500.0),
            Item("마늘통닭", 400.0),
            Item("페페로니 피자", 250.0),
            Item("포테이토 피자", 10.0),
        )

        val itemTags = arrayListOf(
            listOf(ItemTag("후라이드"))
        )

//        itemRequests.forEach { request ->
//            itemRepository.save(Item(name = request.first, price = request.second))
//        }
//
//        val itemTagRequests = listOf(
//            "달콤한 맛", "상큼한 맛", "쌀쌀한 맛", "신맛", "달달한 맛", "알싸한 맛"
//        )
//
//        itemTagRequests.forEach { request ->
//            itemTagRepository.save(ItemTag(request))
//        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbootGqlBasisApplication>(*args)
}
