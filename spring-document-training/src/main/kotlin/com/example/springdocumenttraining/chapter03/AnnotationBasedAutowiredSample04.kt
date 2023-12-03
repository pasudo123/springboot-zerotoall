package com.example.springdocumenttraining.chapter03

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service

class AnnotationBasedAutowiredSample04

class StudySource

@Configuration
class CustomPrimaryConfiguration {

    @Bean
    fun mainStudySource(): StudySource {
        return StudySource()
    }

    @Bean
    fun secondStudySource(): StudySource {
        return StudySource()
    }
}

@Service
class StudySourceProvider(
    // 어느 타입을 빈으로 주입할지 IoC 컨테이너는 알 수 없음
    @Qualifier("mainStudySource")
    private val studySource: StudySource
)