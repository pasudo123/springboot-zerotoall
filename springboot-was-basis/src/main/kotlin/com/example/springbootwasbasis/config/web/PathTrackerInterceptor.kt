package com.example.springbootwasbasis.config.web

import com.example.springbootwasbasis.config.annotation.PathTracker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.HandlerMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PathTrackerInterceptor : HandlerInterceptor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val exist = handler is HandlerMethod && handler.hasMethodAnnotation(PathTracker::class.java)
        if (exist.not()) return

        val pathVariables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as Map<*, *>
        pathVariables.logging()
    }

    private fun Map<*, *>.logging() {
        this.keys.forEach { log.info("key=$it, value=${this[it]}") }
    }
}