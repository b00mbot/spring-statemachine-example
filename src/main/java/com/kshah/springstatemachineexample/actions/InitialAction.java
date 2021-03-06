package com.kshah.springstatemachineexample.actions;

import com.kshah.springstatemachineexample.model.Events;
import com.kshah.springstatemachineexample.model.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class InitialAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        log.info("Entered state: Initial");
        log.info("Initializing...");
    }

}
