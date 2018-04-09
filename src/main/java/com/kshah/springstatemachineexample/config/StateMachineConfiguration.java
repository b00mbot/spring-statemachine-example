package com.kshah.springstatemachineexample.config;

import com.kshah.springstatemachineexample.actions.EndTransitionAction;
import com.kshah.springstatemachineexample.actions.InitialAction;
import com.kshah.springstatemachineexample.actions.ProcessingAction;
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

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<States, Events> {


    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {

        // Configure states
        states.withStates()
                // Initial state
                .initial(States.INITIAL, initialAction())
                // Processing state
                .state(States.PROCESSING, processingAction())
                // End state
                .end(States.DONE);
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                // Transition: Initial - Processing
                .withExternal()
                .source(States.INITIAL).target(States.PROCESSING).event(Events.START_PROCESSING)
                .and()
                // Transition: Processing - Finished Processing
                .withExternal()
                .source(States.PROCESSING).target(States.DONE).event(Events.FINISHED_PROCESSING).action(endTransitionAction());
    }


    @Bean
    protected Action<States, Events> initialAction() {
        return new InitialAction();
    }


    @Bean
    protected Action<States, Events> processingAction() {
        return new ProcessingAction();
    }


    @Bean
    protected Action<States, Events> endTransitionAction() {
        return new EndTransitionAction();
    }


}
