package com.example.springdocumenttraining.chapter03

import org.springframework.beans.factory.FactoryBean
import org.springframework.stereotype.Component

class BeanFactoryBeanSample

class Rice(
    val name: String
)

@Component("rice1")
class RiceFactoryBean : FactoryBean<Rice> {
    override fun getObject(): Rice {
        return Rice("흑미밥")
    }

    override fun getObjectType(): Class<*> {
        return Rice::class.java
    }

    override fun isSingleton(): Boolean {
        return true
    }
}