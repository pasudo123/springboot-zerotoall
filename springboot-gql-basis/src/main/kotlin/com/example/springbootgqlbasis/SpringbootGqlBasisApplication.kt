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

        val itemRequests = listOf(
            Pair("사과", 500.0),
            Pair("복숭아", 300.0),
            Pair("딸기", 2500.0),
            Pair("망고", 400.0),
            Pair("참외", 250.0),
            Pair("포도", 10.0),
        )

        itemRequests.forEach { request ->
            itemRepository.save(Item(name = request.first, price = request.second))
        }

        val itemTagRequests = listOf(
            "달콤한 맛", "상큼한 맛", "쌀쌀한 맛", "신맛", "달달한 맛", "알싸한 맛"
        )

        itemTagRequests.forEach { request ->
            itemTagRepository.save(ItemTag(request))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbootGqlBasisApplication>(*args)
}
