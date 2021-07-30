package com.example.springbootmongobasis.runner

import mu.KLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(value = ["dev", "local", "default"])
@Component
class DataInitializer(
    private val dataStudentInitializer: DataStudentInitializer,
    private val dataBannerInitializer: DataBannerInitializer,
    private val dataLectureInitializer: DataLectureInitializer,
    private val dataBusInitializer: DataBusInitializer
) : CommandLineRunner{

    companion object : KLogging()

    override fun run(vararg args: String?) {
        dataStudentInitializer.process()
        dataBannerInitializer.process()
        dataLectureInitializer.process()
        dataBusInitializer.process()
    }
}