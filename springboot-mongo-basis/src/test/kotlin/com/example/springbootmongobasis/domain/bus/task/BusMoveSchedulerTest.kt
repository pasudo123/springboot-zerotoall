package com.example.springbootmongobasis.domain.bus.task

import com.example.springbootmongobasis.MongoRepositorySupport
import com.example.springbootmongobasis.domain.bus.api.dto.BusDto
import com.example.springbootmongobasis.domain.bus.model.Bus
import com.example.springbootmongobasis.domain.bus.model.BusLocation
import com.example.springbootmongobasis.domain.bus.repository.BusRepository
import com.example.springbootmongobasis.util.toLineString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import java.util.UUID

@MongoRepositorySupport
@DisplayName("BusMoveScheduler 는")
@TestPropertySource(
    locations = ["/application.yml", "/application-test.yml"],
    properties = [
        "app.schedule.banner.enabled=false",
        "app.schedule.busmove.enabled=true"
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BusMoveSchedulerTest {

    @Autowired
    private lateinit var busMoveScheduler: BusMoveScheduler

    @Autowired
    private lateinit var busRepository: BusRepository

    @Test
    @DisplayName("스케줄을 수행한다.")
    fun scheduleTest() {

        // init : 버스 데이터를 밀어 넣는다.
        repeat(50) {
            val request = BusDto.CreateRequest(
                name = "${UUID.randomUUID().toLineString()}-BUS"
            )
            busRepository.save(Bus.from(request))
        }

        repeat(50) {
            this.busMove()
            busMoveScheduler.findPossibleCallBusesTask()
        }
    }

    private fun busMove() {
        val buses = busRepository.findAll()
        val newBuses = buses.map { bus ->
            bus.changeLocation(locations.shuffled().first())
        }

        busRepository.saveAll(buses)
    }
}

val locations = listOf<BusLocation>(
    BusLocation(37.480846756393504, 126.95230748654934, "서울대입구역 애슐리"),
    BusLocation(37.4815425038093, 126.95225416466225, "서울대입구역 스타벅스"),
    BusLocation(37.48128882676235, 126.95335945042939, "서울대입구역 연세에이치 치과의원"),
    BusLocation(37.48090319257077, 126.95312855126616, "서울대입구역 1번출구"),
    BusLocation(37.480800047690146, 126.95284094048023, "서울대입구역 2번출구"),
    BusLocation(37.48130479787718, 126.95319768847433, "서울대입구역 8번출구"),
    BusLocation(37.48213041109874, 126.94264637658792, "봉천역 1번출구"),
    BusLocation(37.482194052568346, 126.94223431882729, "봉천역 2번출구"),
    BusLocation(37.48243325622794, 126.94272104477274, "봉천역 6번출구"),
    BusLocation(37.482887521620206, 126.94085434015241, "봉천역 4번출구"),
    BusLocation(37.48262198519131, 126.94066905238293, "봉천역 3번출구"),
    BusLocation(37.484157030001136, 126.93027610275647, "신림역 1번출구"),
    BusLocation(37.48386516427679, 126.92957643420989, "신림역 3번출구"),
    BusLocation(37.484014389000116, 126.92918650035587, "신림역 4번출구"),
    BusLocation(37.48451033961562, 126.92946858016515, "신림역 6번출구"),
    BusLocation(37.477191216294734, 126.96273972042718, "낙성대역 4번출구"),
    BusLocation(37.476971748029406, 126.96327622516247, "낙성대역 3번출구"),
    BusLocation(37.47676325258035, 126.96383485380441, "낙성대역 2번출구"),
    BusLocation(37.476550367153266, 126.96435200012145, "낙성대역 1번출구"),
    BusLocation(37.47682250924084, 126.96450133649107, "낙성대역 8번출구")

)
