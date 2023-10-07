package com.example.springboot3reactivewithresilience4j.poc

import com.example.springboot3reactivewithresilience4j.Metrics
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalTime

class DemoResources {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class DemoResponse(
        val text: String,
        val time: LocalTime,
        val metrics: Metrics,
        val cause: String? = null
    )
}
