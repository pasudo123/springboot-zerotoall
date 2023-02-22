package com.example.springbootstatemahcinebasis.config

import com.example.springbootstatemahcinebasis.resources.Events
import com.example.springbootstatemahcinebasis.resources.States
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
import java.util.EnumSet

@Configuration
@EnableStateMachine
class CustomStateMachineConfiguration : StateMachineConfigurerAdapter<States, Events>() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun configure(config: StateMachineConfigurationConfigurer<States, Events>) {

        config
            .withConfiguration()
            .autoStartup(true)
            .listener(listeners())
    }

    @Bean
    fun listeners(): StateMachineListenerAdapter<States, Events> {

        return object: StateMachineListenerAdapter<States, Events>() {
            override fun stateChanged(from: State<States, Events>?, to: State<States, Events>?) {
                log.info("State changed to ${to?.id}")
            }
        }
    }

    override fun configure(states: StateMachineStateConfigurer<States, Events>) {
        states.withStates()
            .initial(States.SI)
            .states(EnumSet.allOf(States::class.java))
    }

    override fun configure(transitions: StateMachineTransitionConfigurer<States, Events>) {
        transitions
            .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .and()
            .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2)
    }
}