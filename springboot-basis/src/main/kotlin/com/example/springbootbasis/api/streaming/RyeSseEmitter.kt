package com.example.springbootbasis.api.streaming

import org.slf4j.LoggerFactory
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

class RyeSseEmitter(timeoutMilli: Long = 60000) : SseEmitter(timeoutMilli) {

    private val log = LoggerFactory.getLogger(javaClass)

    init {
        this.onCompletion()
        this.onError()
        this.onTimeout()
    }

    private fun onCompletion() {
        super.onCompletion {
            log.info("### complete")
        }
    }

    private fun onError() {
        super.onError {
            log.error("### error : ${it.message}")
        }
    }

    private fun onTimeout() {
        super.onTimeout {
            log.error("### timeout")
        }
    }

    /**
     * sse response 에서 한글이 깨지기 때문에 인코딩타입을 맞춰준다.
     * 디폴트는 ISO-8859-1 로 잡혀있어서 한글이 깨진다.
     */
    override fun extendResponse(outputMessage: ServerHttpResponse) {
        when (outputMessage) {
            is ServletServerHttpResponse -> outputMessage.servletResponse.characterEncoding = "UTF-8"
        }
        super.extendResponse(outputMessage)
    }
}
