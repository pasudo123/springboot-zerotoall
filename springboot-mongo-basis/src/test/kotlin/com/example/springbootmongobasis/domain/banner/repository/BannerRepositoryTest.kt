package com.example.springbootmongobasis.domain.banner.repository

import com.example.springbootmongobasis.MongoRepositoryTest
import com.example.springbootmongobasis.domain.banner.api.dto.BannerDto
import com.example.springbootmongobasis.domain.banner.model.Banner
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import kotlin.system.measureNanoTime
import kotlin.time.measureTimedValue

@MongoRepositoryTest
@DisplayName("BannerRepository 는")
class BannerRepositoryTest {

    val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var bannerRepository: BannerRepository

    @ParameterizedTest
    @ValueSource(ints = [1000, 10000, 100000])
    @DisplayName("일반 인덱스에 대한 성능측정을 한다.")
    fun performanceTestOnIndexed(repeatCount: Int) {
        logger.info { "do test : $repeatCount" }

        // given
        bannerRepository.deleteAll()

        val names: MutableList<String> = mutableListOf()
        repeat(repeatCount) { sequence ->
            val banner = createBanner(sequence)
            bannerRepository.save(banner)
            names.add(banner.name)
        }

        // when
        val name = names.shuffled().first()

        // then
        val nanoTime = measureNanoTime {
            val foundBanner = bannerRepository.findByName(name).get()
            foundBanner.name shouldBe name
        }

        // 1000개, 34778067 nano second
        // 10000개, 13417059 nano second
        // 100000개, 101310293 nano second
        logger.info { "count[$repeatCount], nanotime elapse[${nanoTime}]" }
    }

    @ParameterizedTest
    @ValueSource(ints = [1000, 10000, 100000])
    @DisplayName("해시 인덱스에 대한 성능측정을 한다.")
    fun performanceTestOnHashedIndexed(repeatCount: Int) {

        // given
        bannerRepository.deleteAll()

        val hashNames: MutableList<String> = mutableListOf()
        repeat(repeatCount) { sequence ->
            val banner = createBanner(sequence)
            bannerRepository.save(banner)
            hashNames.add(banner.hashName)
        }

        // when
        val hashName = hashNames.shuffled().first()

        // then
        val nanoTime = measureNanoTime {
            val foundBanner = bannerRepository.findByHashName(hashName).get()
            foundBanner.hashName shouldBe hashName
        }

        // hashIndexed
        // 1000개,  49459147 nano second
        // 10000개,  8566209 nano second
        // 100000개,  121183446 nano second
        logger.info { "count[$repeatCount], nanotime elapse[${nanoTime}]" }
    }

    private fun createBanner(sequence: Int): Banner {
        val request = BannerDto.CreateRequest(
            name = "$sequence 광고",
            desc = "$sequence description"
        )

        return Banner.from(request)
    }
}