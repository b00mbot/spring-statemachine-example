package com.kshah.springstatemachineexample.config;

import com.kshah.springstatemachineexample.actions.*;
import com.kshah.springstatemachineexample.guards.TypeAProcessingGuard;
import com.kshah.springstatemachineexample.guards.TypeBProcessingGuard;
import com.kshah.springstatemachineexample.guards.TypeCProcessingGuard;
import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<States, Events> {


    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .machineId(Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
    }


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {

        // Configure states
        states.withStates()
                // Initial state
                .initial(States.INITIAL, initialAction())
                // Processing state
                .choice(States.PROCESSING)
                // Processing Type A state
                .stateEntry(States.PROCESSING_TYPE_A, processingTypeAAction())
                // Processing Type B state
                .stateEntry(States.PROCESSING_TYPE_B, processingTypeBAction())
                // Processing Type C state
                .stateEntry(States.PROCESSING_TYPE_C, processingTypeCAction())
                // Invalid Processing Type state
                .end(States.INVALID_PROCESSING_TYPE)
                // Done state
                .end(States.DONE);
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                // Transition: Initial - Processing
                .withExternal()
                .source(States.INITIAL).target(States.PROCESSING).event(Events.START_PROCESSING)
                .and()
                // Conditional Transition: Processing - Processing Type <>
                .withChoice()
                .source(States.PROCESSING)
                // Conditional Transition: Processing - Processing Type A
                .first(States.PROCESSING_TYPE_A, typeAProcessingGuard())
                // Conditional Transition: Processing - Processing Type B
                .then(States.PROCESSING_TYPE_B, typeBProcessingGuard())
                // Conditional Transition: Processing - Processing Type C
                .then(States.PROCESSING_TYPE_C, typeCProcessingGuard())
                // Conditional Transition: Processing - Invalid Processing Type
                .last(States.INVALID_PROCESSING_TYPE)
                .and()
                // Transition:  Processing Type A - Done
                .withExternal()
                .source(States.PROCESSING_TYPE_A).target(States.DONE).event(Events.FINISHED_PROCESSING).action(endTransitionAction())
                .and()
                // Transition:  Processing Type B - Done
                .withExternal()
                .source(States.PROCESSING_TYPE_B).target(States.DONE).event(Events.FINISHED_PROCESSING).action(endTransitionAction())
                .and()
                // Transition:  Processing Type C - Done
                .withExternal()
                .source(States.PROCESSING_TYPE_C).target(States.DONE).event(Events.FINISHED_PROCESSING).action(endTransitionAction());
    }


    @Bean
    protected Action<States, Events> initialAction() {
        return new InitialAction();
    }


    @Bean
    protected Action<States, Events> processingTypeAAction() {
        return new ProcessingTypeAAction();
    }


    @Bean
    protected Action<States, Events> processingTypeBAction() {
        return new ProcessingTypeBAction();
    }


    @Bean
    protected Action<States, Events> processingTypeCAction() {
        return new ProcessingTypeCAction();
    }


    @Bean
    protected Action<States, Events> endTransitionAction() {
        return new EndTransitionAction();
    }


    @Bean
    protected Guard<States, Events> typeAProcessingGuard() {
        return new TypeAProcessingGuard();
    }


    @Bean
    protected Guard<States, Events> typeBProcessingGuard() {
        return new TypeBProcessingGuard();
    }


    @Bean
    protected Guard<States, Events> typeCProcessingGuard() {
        return new TypeCProcessingGuard();
    }

}
