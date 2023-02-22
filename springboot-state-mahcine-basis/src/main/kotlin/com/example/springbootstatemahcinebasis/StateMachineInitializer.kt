package com.example.springbootstatemahcinebasis

import com.example.springbootstatemahcinebasis.resources.Events
import com.example.springbootstatemahcinebasis.resources.States
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.statemachine.StateMachine
import org.springframework.stereotype.Component

@Component
class StateMachineInitializer(
    private val stateMachine: StateMachine<States, Events>
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        stateMachine.sendEvent(Events.E1)
        stateMachine.sendEvent(Events.E2)
    }
}