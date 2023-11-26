package com.example.springdocumenttraining.chapter03

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class Chapter03Runner(
    private val applicationContext: ApplicationContext,
    // private val myParentBean: MyParentBean,
    // private val myChildBean: MyChildBean,
    // private val myNewChildBean: MyNewChildBean
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // println("myParentBean, name=${myParentBean.name}, age=${myParentBean.age}")
        // println("myChildBean, name=${myChildBean.name}, age=${myChildBean.age}")
        // println("myNewChildBean, name=${myNewChildBean.name}, age=${myNewChildBean.age}")
    }
}