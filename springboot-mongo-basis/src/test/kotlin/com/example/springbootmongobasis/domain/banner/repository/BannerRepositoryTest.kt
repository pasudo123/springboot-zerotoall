package com.example.springbootmongobasis.domain.banner.repository

import com.example.springbootmongobasis.MongoSpringbootTest
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@MongoSpringbootTest
@DisplayName("BannerRepository 는")
class BannerRepositoryTest {

    val logger = KotlinLogging.logger {}

    @ParameterizedTest
    @ValueSource(longs = [1000, 10000, 100000])
    @DisplayName("일반 인덱스에 대한 성능측정을 한다.")
    fun performanceTestOnIndexed(repeatCount: Long) {
        logger.info { "do test : $repeatCount" }
    }

    @ParameterizedTest
    @ValueSource(longs = [1000, 10000, 100000])
    @DisplayName("해시 인덱스에 대한 성능측정을 한다.")
    fun performanceTestOnHashedIndexed(repeatCount: Long) {

    }
}