package com.example.springbootcloudserverbasis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class SpringbootCloudServerBasisApplication

fun main(args: Array<String>) {
    runApplication<SpringbootCloudServerBasisApplication>(*args)
}
