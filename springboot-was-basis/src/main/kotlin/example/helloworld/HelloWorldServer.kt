package example.helloworld

import io.undertow.Undertow
import io.undertow.util.Headers

class HelloWorldServer

fun main() {

    val undertow = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler { exchange ->
            exchange.responseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
            exchange.responseSender.send("Hello World!!")
        }
        .build()

    undertow.start()
}