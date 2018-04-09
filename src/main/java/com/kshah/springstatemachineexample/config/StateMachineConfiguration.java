package com.kshah.springstatemachineexample.config;

import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<States, Events> {


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {

        // Configure states
        states.withStates()
                // Initial state
                .initial(States.INITIAL)
                // End state
                .end(States.DONE)
                // All other states
                .states(
                        new HashSet<>(Collections.singletonList(States.PROCESSING))
                );
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                // Transition: Initial - Processing
                .withExternal()
                .source(States.INITIAL).target(States.PROCESSING).event(Events.START_PROCCESSING)
                .and()
                // Transition: Processing - Finished Processing
                .withExternal()
                .source(States.PROCESSING).target(States.DONE).event(Events.FINISHED_PROCESSING);
    }

}
