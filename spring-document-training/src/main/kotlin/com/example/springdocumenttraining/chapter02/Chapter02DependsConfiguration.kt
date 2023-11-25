//package com.example.springdocumenttraining.chapter02
//
//import jakarta.annotation.PostConstruct
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.annotation.Order
//
//@Configuration
//class Chapter02DependsConfiguration {
//
//    class ABean(
//        private val dBean: DBean
//    ) {
//        @PostConstruct
//        fun init() {
//             println(">>> ABean bean init")
//        }
//    }
//
//    class DBean {
//        @PostConstruct
//        fun init() {
//            println(">>> DBean bean init")
//        }
//    }
//
//    @Bean
//    fun aBean(
//    ): ABean {
//        return ABean(dBean())
//    }
//
//    @Bean
//    fun dBean(): DBean {
//        return DBean()
//    }
//}
