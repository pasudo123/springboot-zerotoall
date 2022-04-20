package com.example.springbootbasis.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class CustomHolderAspect {

    @Before(value = "@annotation(com.example.springbootbasis.annotation.HolderAnnotation)")
    fun getNameByType(joinPoint: JoinPoint) {

        val signature = joinPoint.signature as MethodSignature
    }
}