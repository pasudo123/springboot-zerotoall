package com.example.springdocumenttraining.chapter02

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class Chapter02LazyConfiguration {

    @Bean
    @Lazy
    fun motherLazyBean(): MotherLazyBean {
        println("create motherLazyBean")
        return MotherLazyBean()
    }

    @Bean
    fun motherNowBean(): MotherNowBean {
        println("create motherNowBean")
        return MotherNowBean()
    }

    class MotherLazyBean {

        fun use() {
            println("use, mother lazy bean")
        }
    }

    class MotherNowBean {
        fun use() {
            println("use, mother now bean")
        }
    }
}