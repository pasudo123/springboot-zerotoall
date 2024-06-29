package com.example.springbootmongobasis.init

import com.example.springbootmongobasis.init.sub.DataCafeInitializer
import mu.KLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(value = ["dev", "local", "default"])
@Component
class DataInitializer(
    private val dataCafeInitializer: DataCafeInitializer
) : CommandLineRunner {

    companion object : KLogging()

    override fun run(vararg args: String?) {
        dataCafeInitializer.bulkInsert()
    }
}
