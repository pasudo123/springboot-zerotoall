package com.example.springdocumenttraining.chapter02

import org.springframework.beans.factory.BeanNameAware
import org.springframework.context.Lifecycle
import org.springframework.context.LifecycleProcessor
import org.springframework.context.SmartLifecycle
import org.springframework.stereotype.Component

class LifeCycle02Sample

@Component
class LifeCycleImpl : Lifecycle {
    var isCustomRunning = false

    override fun start() {
        isCustomRunning = true
        println("LifeCycleImpl start")
    }

    override fun stop() {
        isCustomRunning = false
        println("LifeCycleImpl stop")
    }

    override fun isRunning(): Boolean {
        println("LifeCycleImpl isRunning")
        return isCustomRunning
    }
}

@Component
class LifeCycleProcessImpl : LifecycleProcessor {
    var isCustomRunning = false

    override fun start() {
        isCustomRunning = true
        println("LifeCycleProcessImpl start")
    }

    override fun stop() {
        isCustomRunning = false
        println("LifeCycleProcessImpl stop")
    }

    override fun isRunning(): Boolean {
        println("LifeCycleProcessImpl isRunning")
        return isCustomRunning
    }

    override fun onRefresh() {
        println("LifeCycleProcessImpl onRefresh")
    }

    override fun onClose() {
        println("LifeCycleProcessImpl onClose")
    }
}

@Component
class SmartLifeCycleImpl : SmartLifecycle, LifecycleProcessor, BeanNameAware {
    var isCustomRunning = false

    override fun start() {
        isCustomRunning = true
        println("SmartLifeCycleImpl start")
    }

    override fun stop() {
        isCustomRunning = false
        println("SmartLifeCycleImpl stop")
    }

    override fun isRunning(): Boolean {
        println("SmartLifeCycleImpl isRunning")
        return isCustomRunning
    }

    override fun onRefresh() {
        println("SmartLifeCycleImpl onRefresh")
    }

    override fun onClose() {
        println("SmartLifeCycleImpl onClose")
    }

    override fun isAutoStartup(): Boolean {
        return true
    }

    override fun setBeanName(name: String) {
        println("beanName=$name")
    }
}
