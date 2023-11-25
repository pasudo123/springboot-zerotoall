//package com.example.springdocumenttraining.chapter02
//
//import org.springframework.beans.factory.annotation.Lookup
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Scope
//import org.springframework.stereotype.Component
//
//@Configuration
//class Chapter02MethodInjectionConfiguration {
//
//    @Bean
//    @Scope("prototype")
//    fun protoTypeSampleBean(): ProtoTypeSampleBean {
//        return ProtoTypeSampleBean()
//    }
//}
//
//class ProtoTypeSampleBean
//
//@Component
//abstract class SingleTonSampleBean1th {
//
//    @Lookup("protoTypeSampleBean")
//    // 미구현이라도 @Lookup 처리로 인하여 CGLIB 프록시가 생성되어 오버라이딩 처리가 된다
//    abstract fun createProtoType(): ProtoTypeSampleBean
//}
//
//@Component
//abstract class SingleTonSampleBean2th {
//
//    @Lookup("protoTypeSampleBean")
//    abstract fun createProtoType(): ProtoTypeSampleBean
//}
