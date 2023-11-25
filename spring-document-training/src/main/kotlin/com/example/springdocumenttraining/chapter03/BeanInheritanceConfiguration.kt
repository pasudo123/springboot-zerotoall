package com.example.springdocumenttraining.chapter03

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanInheritanceConfiguration {

    @Bean
    fun myChildBean(): MyChildBean {
        return MyChildBean().apply {
            this.name = "이순신"
            this.age = 30
        }
    }

    @Bean
    fun myNewChildBean(): MyNewChildBean {
        return MyNewChildBean().apply {
            this.name = "이성계"
            this.age = 40
        }
    }
}

abstract class MyParentBean {
    var name: String? = null
    var age: Int? = null
}

class MyChildBean : MyParentBean()

class MyNewChildBean: MyParentBean()