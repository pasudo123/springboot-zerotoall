package com.example.springbootwasbasis

import org.slf4j.LoggerFactory
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@Component
class MyUndertowCustomizer : WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun customize(factory: UndertowServletWebServerFactory?) {

        log.info("@@@@@ undertow customize")

        factory?.apply {
            this.port = 8080
            this.setIoThreads(2)
            this.setWorkerThreads(1)
            this.addBuilderCustomizers(
                UndertowBuilderCustomizer {
//                it.setWorkerOption(UndertowOptions.IDLE_TIMEOUT, 3000)
//                it.setWorkerOption(UndertowOptions.NO_REQUEST_TIMEOUT, 3000)
                }
            )
        }
    }
}
