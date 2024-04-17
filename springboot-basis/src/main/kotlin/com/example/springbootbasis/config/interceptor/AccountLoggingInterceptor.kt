package com.example.springbootbasis.config.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

@Component
class AccountLoggingInterceptor : HandlerInterceptor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("pre-handle.request.uri=${request.requestURI}")
            this.appendLine("pre-handle.request.header-names=${request.headerNames}")
        }

        log.info(lines.toString())
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("post-handle.request.uri=${request.requestURI}")
        }

        log.info(lines.toString())
        super.postHandle(request, response, handler, modelAndView)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("after-completion.request.uri=${request.requestURI}")
        }

        log.info(lines.toString())
    }
}
