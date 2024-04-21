package com.example.springbootgrpcbasis.protobuf

import com.example.proto.sample.HelloRequest
import com.example.proto.sample.HelloResponse
import com.example.springbootgrpcbasis.client.MockClient
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@RequestMapping("simple-protobuf")
class SimpleProtobufController(
    private val mockClient: MockClient
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(
        path = ["hello"],
        produces = [MediaType.APPLICATION_PROTOBUF_VALUE],
        consumes = [MediaType.APPLICATION_PROTOBUF_VALUE]
    )
    fun helloToServer(
        @RequestBody request: HelloRequest
    ): HelloResponse {
        val name = request.name
        return HelloResponse.newBuilder()
            .setMessage("hello-$name(${LocalTime.now()})")
            .build()
    }

    @GetMapping
    fun hello(@RequestParam("message") message: String): String {
        val request = HelloRequest.newBuilder()
            .setName(message)
            .build()

        val call = mockClient.hello(request).execute()

        if (call.isSuccessful) {
            val response = call.body()!!
            return """
                {
                    "message": "${response.message}"
                }
            """.trimIndent()
        }

        log.error(call.errorBody()?.string() ?: "empty?")

        return """
            {
                "message": "에러발생, 코드확인."
            }
        """.trimIndent()
    }
}
