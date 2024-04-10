package com.example.springbootbasis.config.interceptor

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.ui.ModelMap
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.WebRequestInterceptor

@Component
class AccountLoggingWebInterceptor : WebRequestInterceptor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun preHandle(request: WebRequest) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("web.pre-handle.request.uri=${(request as ServletWebRequest).request.requestURI}")
            this.appendLine("web.pre-handle.request.header-names=${request.headerNames}")
        }

        log.info(lines.toString())
    }

    override fun postHandle(request: WebRequest, model: ModelMap?) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("web.post-handle.request.uuri=${(request as ServletWebRequest).request.requestURI}")
        }

        log.info(lines.toString())
    }

    override fun afterCompletion(request: WebRequest, ex: Exception?) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("web.after-completion.request.uri=${(request as ServletWebRequest).request.requestURI}")
        }

        log.info(lines.toString())
    }
}