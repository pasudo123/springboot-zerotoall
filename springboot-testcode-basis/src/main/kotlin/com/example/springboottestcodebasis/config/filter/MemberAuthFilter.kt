package com.example.springboottestcodebasis.config.filter

import com.example.springboottestcodebasis.constant.Constant
import com.example.springboottestcodebasis.domain.member.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLoggable
import org.springframework.http.HttpStatus
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * filter 에서 발생하는 익셉션은 SpringContext 범위를 벗어난다.
 * 따라서 ControllerAdvice 에서 익셉션을 캐치할 수 없다.
 * - https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring
 * - 생각해보니, 필터에서 로깅만을 처리하고, 인증처리는 인터셉터에서 하는게 Controller Advice 에서 잡을 수 있는 이점도 있고 테스트 용이성이 높아 그게 좋을 듯 하다.
 */
class MemberAuthFilter(
    private val objectMapper: ObjectMapper,
    private val memberRepository: MemberRepository,
) : Filter {

    companion object : Any(), KLoggable {
        override val logger = logger()
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        logger.info { "====== [request - start] ======" }
        logger.info { "- [method] ${httpRequest.method}" }
        logger.info { "- [uri] ${httpRequest.requestURI}" }

        val headerNames = httpRequest.headerNames
        while (headerNames.hasMoreElements()) {
            val currentHeaderName = headerNames.nextElement()
            val currentHeaderValue = httpRequest.getHeader(currentHeaderName)
            logger.info { "- [header] $currentHeaderName : $currentHeaderValue" }
        }

        if (httpRequest.getHeader(Constant.PermissionHeader.KEY).isNullOrBlank()) {
            httpResponse.apply {
                this.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            }
            return
        }

        if (httpRequest.getHeader(Constant.PermissionHeader.KEY) != Constant.PermissionHeader.ADMIN.VALUE) {
            httpResponse.apply {
                this.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            }
            return
        }

        val members = memberRepository.findAll()
        members.forEach { member ->
            logger.info { "${member.name}" }
        }

        chain?.doFilter(request, response)

        logger.info { "====== [request - end] ======" }
    }
}