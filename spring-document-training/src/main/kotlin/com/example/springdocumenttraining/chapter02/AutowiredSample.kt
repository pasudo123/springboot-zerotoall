package com.example.springdocumenttraining.chapter02

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils

class AutowiredSample

@Component
class ParentBean(
    private var childBean: ChildBean,
    private val applicationContext: ApplicationContext
) {

    init {
        applicationContext.getBean(ChildBean::class.java).also {
            println("init >> ${ObjectUtils.identityToString(it)}")
        }
        println("init childBean : $childBean")
    }

    @Autowired
    fun setChildBean(childBean: ChildBean) {
        applicationContext.getBean(ChildBean::class.java).also {
            println("setter >> ${ObjectUtils.identityToString(it)}")
        }

        println("setter childBean : $childBean")
        this.childBean = childBean
    }
}

@Component
class ChildBean
