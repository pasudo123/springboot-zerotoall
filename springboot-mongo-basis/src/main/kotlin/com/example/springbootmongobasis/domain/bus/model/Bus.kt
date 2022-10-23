package com.example.springbootmongobasis.domain.bus.model

import com.example.springbootmongobasis.domain.BaseDocument
import com.example.springbootmongobasis.domain.bus.api.dto.BusDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "bus")
class Bus private constructor(
    val name: String,
): BaseDocument() {

    @Id
    var id: String? = null
        protected  set

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    var location: Array<Double>? = null
        protected  set

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    var location2dSphere: Array<Double>? = null
        protected set

    var available: Boolean = false

    fun dispatchPossible() {
        this.available = true
    }

    fun dispatchImpossible() {
        this.available = false
    }

    fun changeLocation(busLocation: BusLocation) {
        this.location = busLocation.convertToLocation()
        this.location2dSphere = busLocation.convertToLocation()
    }

    companion object {
        fun from(request: BusDto.CreateRequest): Bus {
            return request.run {
                Bus(this.name)
            }
        }
    }
}