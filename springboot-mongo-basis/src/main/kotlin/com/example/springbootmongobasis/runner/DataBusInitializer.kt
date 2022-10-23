package com.example.springbootmongobasis.runner

import com.example.springbootmongobasis.domain.bus.api.dto.BusDto
import com.example.springbootmongobasis.domain.bus.model.Bus
import com.example.springbootmongobasis.domain.bus.repository.BusRepository
import com.example.springbootmongobasis.util.toLineString
import mu.KLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DataBusInitializer(
    private val busRepository: BusRepository
) {

    companion object : KLogging()

    fun process() {
        busRepository.deleteAll()
        insert()
    }

    private fun insert(): List<Bus> {

        val buses: MutableList<Bus> = mutableListOf()

        val request = BusDto.CreateRequest(
            name = "${UUID.randomUUID().toLineString()}-BUS"
        )

        buses.add(busRepository.save(Bus.from(request)))

        return buses
    }
}
