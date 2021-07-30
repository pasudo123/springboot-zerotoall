package com.example.springbootmongobasis.domain.bus.model

/**
 * @lat : 위도(y축 : 적도 기준으로 북쪽, 남쪽으로 얼마나 떨어졌는지를 의미)
 * @lng : 경도(x축 : 본초자오선 기준으로 서쪽, 동쪽으로 얼마나 떨어졌는지를 의미)
 */
data class BusLocation(
    val lat: Double,
    val lng: Double,
    val name: String? = null
) {

    fun convertToLocation(): Array<Double> {
        return arrayOf(lng, lat)
    }
}