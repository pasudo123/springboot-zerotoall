//package com.example.springdocumenttraining.chapter01
//
//class SampleOfDip
//
//fun main() {
//
//}
//
//interface DiscountPolicy {
//    fun discount(): Long
//}
//
//class AmountDiscountPolicy : DiscountPolicy {
//    override fun discount(): Long {
//        TODO("Not yet implemented")
//    }
//}
//
//class PercentDiscountPolicy : DiscountPolicy {
//    override fun discount(): Long {
//        TODO("Not yet implemented")
//    }
//}
//
//class NoneDiscountPolicy : DiscountPolicy {
//    override fun discount(): Long {
//        TODO("Not yet implemented")
//    }
//}
//
//class MovieDiscountService(
//    private val discountPolicy: DiscountPolicy
//) {
//
//    fun discountFrom(product: Any) {
//        discountPolicy.discount(/** 파라미터 설정 **/)
//    }
//}